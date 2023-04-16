package golf.example.distributedlock

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container

open class TestContainer {

    companion object {
        private const val REDIS_IMAGE = "redis:latest"
        private const val MYSQL_IMAGE = "mysql:latest"

        @JvmStatic
        @Container
        protected val REDIS_CONTAINER: GenericContainer<Nothing> = GenericContainer<Nothing>(REDIS_IMAGE)
            .apply { withExposedPorts(6379) }
            .apply { withReuse(true) }
            .apply { start() }

        @JvmStatic
        @Container
        val MYSQL_CONTAINER: GenericContainer<Nothing> = GenericContainer<Nothing>(MYSQL_IMAGE)
            .apply { withExposedPorts(3306) }
            .apply { withReuse(true) }
            .apply { withEnv("MYSQL_USER", "nokt") }
            .apply { withEnv("MYSQL_PASSWORD", "1234") }
            .apply { withEnv("MYSQL_DATABASE", "test") }
            .apply { start() }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.redis.host", REDIS_CONTAINER::getHost)
            registry.add("spring.redis.port", REDIS_CONTAINER::getFirstMappedPort)
        }
    }
}