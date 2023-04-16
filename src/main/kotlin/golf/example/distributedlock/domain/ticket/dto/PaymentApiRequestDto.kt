package golf.example.distributedlock.domain.ticket.dto

data class PaymentApiRequestDto(
    val stock: Int,
    val accountNumber: String,
) {
    fun toServiceDto(memberId: Long, ticketId: Long): PaymentRequestDto {
        return PaymentRequestDto(ticketId, stock, accountNumber, memberId)
    }
}
