package golf.example.distributedlock.global.security

import golf.example.distributedlock.domain.member.error.MemberException
import golf.example.distributedlock.domain.member.model.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    var memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(memberId: String): CustomUserDetails =
        memberRepository.getDetailByMemberId(memberId.toLong())
            ?: throw MemberException.MemberNotFoundException(memberId.toLong())

    fun loadUserByEmail(email: String): CustomUserDetails =
        memberRepository.getDetailByEmail(email) ?: throw MemberException.MemberNotFoundException()
}
