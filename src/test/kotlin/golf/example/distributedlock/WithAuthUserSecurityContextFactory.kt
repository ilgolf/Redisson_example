package golf.example.distributedlock

import golf.example.distributedlock.domain.member.model.RoleType
import golf.example.distributedlock.global.security.CustomUserDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithAuthUserSecurityContextFactory: WithSecurityContextFactory<WithAuthUser> {

    override fun createSecurityContext(annotation: WithAuthUser?): SecurityContext {
        val member = GivenMember.toMember()

        val role = AuthorityUtils.createAuthorityList(RoleType.USER.name)

        val userDetails = CustomUserDetails.of(member)

        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(userDetails, "", role)

        return SecurityContextHolder.getContext()
    }
}
