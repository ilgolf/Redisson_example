package golf.example.distributedlock.domain.payment.model

import golf.example.distributedlock.domain.payment.model.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository: JpaRepository<Payment, Long>