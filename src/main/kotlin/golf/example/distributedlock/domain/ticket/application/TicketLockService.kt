package golf.example.distributedlock.domain.ticket.application

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class TicketLockService {

    fun <T> withLock(redissonClient: RedissonClient,
                     lockName: String,
                     lockWaitTime: Long = 10,
                     lockLeaseTime: Long = 10,
                     action: () -> T): T {

        val lock = redissonClient.getLock(lockName)

        return try {
            if (lock.tryLock(lockWaitTime, lockLeaseTime, TimeUnit.SECONDS)) {
                action()
            } else {
                throw IllegalArgumentException("")
            }
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }
}