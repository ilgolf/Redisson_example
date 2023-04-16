package golf.example.distributedlock.domain.ticket.dto

data class PaymentRequestDto(
    val ticketId: Long,
    val stock: Int,
    val accountNumber: String,
    val memberId: Long
)
