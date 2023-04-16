package golf.example.distributedlock.domain.ticket.presentation

import golf.example.distributedlock.domain.ticket.application.TicketService
import golf.example.distributedlock.domain.ticket.dto.PaymentApiRequestDto
import golf.example.distributedlock.domain.ticket.dto.PaymentResponseDto
import golf.example.distributedlock.global.security.CustomUserDetails
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 구매 책임 다르기 때문에 분리
 *
 * flow : 구매 가능한 티켓 조회 -> 결제 요청(무통장 결제만 가능) -> 입금할 계좌 입력
 */

@RestController
@RequestMapping("/payments")
class TicketPaymentController(
    private val ticketService: TicketService
) {

    /**
     * ticket 구매 요청
     */
    @PostMapping("/{ticketId}")
    fun payTicket(
        @AuthenticationPrincipal customUserDetails: CustomUserDetails,
        @Validated @RequestBody request: PaymentApiRequestDto,
        @PathVariable ticketId: Long
    ): ResponseEntity<PaymentResponseDto> {

        return ResponseEntity.ok(ticketService.payment(
            request.toServiceDto(
                memberId = customUserDetails.memberId,
                ticketId = ticketId)
        ))
    }
}