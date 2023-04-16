package golf.example.distributedlock.domain.ticket.application

import golf.example.distributedlock.GivenMember
import golf.example.distributedlock.TestContainer
import golf.example.distributedlock.domain.member.model.repository.MemberRepository
import golf.example.distributedlock.domain.ticket.dto.PaymentRequestDto
import golf.example.distributedlock.domain.ticket.dto.PaymentResponseDto
import golf.example.distributedlock.domain.ticket.model.Ticket
import golf.example.distributedlock.domain.ticket.model.TicketRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@ActiveProfiles("test")
@Transactional
internal class TicketServiceTest

@Autowired
constructor(
    private val tickerService: TicketService,
    private val ticketRepository: TicketRepository,
    private val memberRepository: MemberRepository
) : TestContainer() {

    var memberId: Long = 0
    var targetTicket: Long = 0

    @BeforeEach
    fun init() {
        val standingA = Ticket("STANDING A", BigDecimal(150000), 500)
        val standingB = Ticket("STANDING B", BigDecimal(150000), 500)
        val seatA = Ticket("지정석 R A", BigDecimal(200000), 30)
        val seatB = Ticket("지정석 R B", BigDecimal(200000), 30)

        ticketRepository.save(standingA)
        ticketRepository.save(standingB)
        targetTicket = ticketRepository.save(seatA).id
        ticketRepository.save(seatB)

        memberId = memberRepository.save(GivenMember.toMember()).memberId
    }

    @Test
    @DisplayName("payment 동시성 테스트")
    fun payment() {
        // given
        val threadPool = Executors.newFixedThreadPool(10)
        val latch = CountDownLatch(100)

        val request = PaymentRequestDto(targetTicket, 2, "2993904013", memberId)

        val list = arrayListOf<PaymentResponseDto>()

        // when
        for (i: Int in 0 .. 100) {
            threadPool.execute {
                try {
                    list.add(tickerService.payment(request))
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        // then
        assertThat(list.size).isEqualTo(30)
    }
}