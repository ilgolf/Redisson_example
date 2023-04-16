package golf.example.distributedlock.domain.ticket.dto

import golf.example.distributedlock.domain.payment.model.Payment
import golf.example.distributedlock.domain.payment.model.PaymentStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentCreateDto(
    val ticketId: Long,
    val memberId: Long,
    val accountNumber: String,
    val totalPrice: BigDecimal
) {
    fun toEntity(): Payment {
        return Payment(
            clientAccountNumber = accountNumber,
            totalPrice = totalPrice,
            ticketId = ticketId,
            memberId = memberId,
            paymentTime = LocalDateTime.now(),
            paymentStatus = PaymentStatus.WAITING
        )
    }
}
