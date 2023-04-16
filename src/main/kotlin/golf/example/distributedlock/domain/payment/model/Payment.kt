package golf.example.distributedlock.domain.payment.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Payment(

    @Column(nullable = false)
    val clientAccountNumber: String,

    @Column(nullable = false)
    val totalPrice: BigDecimal,

    @Column(nullable = false)
    val ticketId: Long,

    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false)
    val paymentTime: LocalDateTime,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val paymentStatus: PaymentStatus
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    var id: Long = 0
}