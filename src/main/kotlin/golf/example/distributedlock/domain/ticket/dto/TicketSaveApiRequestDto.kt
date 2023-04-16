package golf.example.distributedlock.domain.ticket.dto

import java.math.BigDecimal

data class TicketSaveApiRequestDto(
    val name: String,
    val price: Int,
    val stock: Int
) {

    fun toServiceDto(): TicketSaveRequestDto {
        return TicketSaveRequestDto(this.name, BigDecimal(this.price), stock)
    }
}
