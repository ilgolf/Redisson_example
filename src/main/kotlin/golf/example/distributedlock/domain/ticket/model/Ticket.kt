package golf.example.distributedlock.domain.ticket.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
class Ticket(

    @Column(length = 100, unique = true, nullable = false)
    var name: String,

    @Column(nullable = false)
    var price: BigDecimal,

    @Column(nullable = false)
    var stock: Int
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ticket

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun isPurchasable(stock: Int): Boolean = this.stock >= stock

    fun decreaseStock(stock: Int) {
        this.stock = this.stock.minus(stock)
    }

    fun calculateTicketPrice(stock: Int): BigDecimal {
        return this.price.multiply(BigDecimal(stock))
    }
}