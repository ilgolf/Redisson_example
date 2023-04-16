package golf.example.distributedlock.domain.ticket.presentation

import golf.example.distributedlock.domain.ticket.application.TicketService
import golf.example.distributedlock.domain.ticket.dto.TicketSaveApiRequestDto
import golf.example.distributedlock.domain.ticket.dto.TicketSummaryResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * STANDING A: stock : 500
 * STANDING B: stock : 500
 * R석 A: stock : 30
 * R석 B: stock : 30
 */

@RestController
@RequestMapping("/tickets")
class TicketController(
    private val ticketService: TicketService
) {

    /**
     * test시 사용할 티켓 생성 API
     */
    @PostMapping
    fun createTicket(@Validated @RequestBody request: TicketSaveApiRequestDto) {
        ticketService.createTicket(request.toServiceDto())
    }

    /**
     * 사기 전 티켓 summary 조회
     */
    @GetMapping
    fun getTickets(@PageableDefault(size = 10) pageable: Pageable): ResponseEntity<Page<TicketSummaryResponseDto>> {
        return ResponseEntity.ok(ticketService.getTickets(pageable))
    }
}