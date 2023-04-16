package golf.example.distributedlock.domain.ticket.dto

import golf.example.distributedlock.domain.ticket.model.Ticket
import java.math.BigDecimal

data class TicketSummaryResponseDto(
    val name: String,
    val ticketId: Long,
    val ticketPrice: BigDecimal,
    val stock: Int
) {

    companion object {
        fun of(ticket: Ticket): TicketSummaryResponseDto {
            return TicketSummaryResponseDto(
                ticket.name,
                ticket.id,
                ticket.price,
                ticket.stock
            )
        }
    }
}
