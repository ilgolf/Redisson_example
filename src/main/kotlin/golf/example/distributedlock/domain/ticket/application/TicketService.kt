package golf.example.distributedlock.domain.ticket.application

import golf.example.distributedlock.domain.payment.application.PaymentService
import golf.example.distributedlock.domain.ticket.dto.*
import golf.example.distributedlock.domain.ticket.model.TicketRepository
import golf.example.distributedlock.domain.ticket.exception.TicketNotFoundException
import golf.example.distributedlock.domain.ticket.exception.TicketPurchaseFailException
import org.redisson.api.RedissonClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.stream.Collectors

@Service
class TicketService(
    private val ticketRepository: TicketRepository,
    private val ticketLockService: TicketLockService,
    private val redissonClient: RedissonClient,
    private val paymentService: PaymentService
) {

    companion object {
        const val ACCOUNT_NUMBER = "123-456-789"
    }

    @Transactional
    fun createTicket(request: TicketSaveRequestDto) {
        ticketRepository.save(request.toEntity())
    }

    @Transactional(readOnly = true)
    fun getTickets(pageable: Pageable): Page<TicketSummaryResponseDto> {
        return PageImpl(ticketRepository.findAll(pageable).stream()
            .map { entity -> TicketSummaryResponseDto.of(entity) }
            .collect(Collectors.toList()))
    }

    @Transactional
    fun payment(request: PaymentRequestDto): PaymentResponseDto {

        val lockName = "${request.ticketId}"
        var totalPrice = BigDecimal(0)

        ticketLockService.withLock(redissonClient, lockName) {
            val ticket = ticketRepository.findByIdOrNull(request.ticketId)
                ?: throw TicketNotFoundException()

            if (!ticket.isPurchasable(request.stock)) {
                throw TicketPurchaseFailException()
            }

            ticket.decreaseStock(request.stock)
            totalPrice = ticket.calculateTicketPrice(request.stock)
        }

        paymentService.createPayment(
            PaymentCreateDto(
                ticketId = request.ticketId,
                memberId = request.memberId,
                accountNumber = request.accountNumber,
                totalPrice = totalPrice)
        )

        return PaymentResponseDto(ACCOUNT_NUMBER)
    }
}
