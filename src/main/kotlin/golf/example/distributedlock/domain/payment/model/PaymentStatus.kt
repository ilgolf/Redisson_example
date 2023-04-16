package golf.example.distributedlock.domain.payment.model

enum class PaymentStatus(
    val description: String
) {

    WAITING("결제 대기중"),
    COMPLETE("결제 완료")
}
