package golf.example.distributedlock

import golf.example.distributedlock.domain.member.model.RoleType
import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory::class)
annotation class WithAuthUser(
    val id: Long,
    val email: String = "ilgolc@naver.com",
    val password: String = "1234",
    val roleType: RoleType = RoleType.USER
)
