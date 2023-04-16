package golf.example.distributedlock.domain.ticket.dto

import golf.example.distributedlock.domain.ticket.model.Ticket
import java.math.BigDecimal

data class TicketSaveRequestDto(
    val name: String,
    val price: BigDecimal,
    val stock: Int
) {

    fun toEntity(): Ticket {
        return Ticket(
            this.name,
            this.price,
            this.stock
        )
    }
}
