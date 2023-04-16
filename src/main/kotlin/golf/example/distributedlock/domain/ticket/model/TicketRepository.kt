package golf.example.distributedlock.domain.ticket.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface TicketRepository: JpaRepository<Ticket, Long> {
}
