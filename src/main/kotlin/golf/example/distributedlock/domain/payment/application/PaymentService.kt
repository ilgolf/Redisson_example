package golf.example.distributedlock.domain.payment.application

import golf.example.distributedlock.domain.payment.model.PaymentRepository
import golf.example.distributedlock.domain.ticket.dto.PaymentCreateDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository
) {

    @Transactional
    fun createPayment(dto: PaymentCreateDto) {
        paymentRepository.save(dto.toEntity())
    }
}