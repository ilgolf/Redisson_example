package golf.example.distributedlock.global.security

import com.querydsl.core.annotations.QueryProjection
import golf.example.distributedlock.domain.member.model.RoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class CustomUserDetails

@QueryProjection
constructor(
    var memberId: Long,
    var email: String,
    var roleType: RoleType
) : UserDetails {

    companion object {
        fun of(member: golf.example.distributedlock.domain.member.model.Member): CustomUserDetails {
            return CustomUserDetails(memberId = member.memberId, email = member.email, roleType = member.roleType)
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return Collections.singleton(SimpleGrantedAuthority("ROLE_${roleType.name}"))
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}