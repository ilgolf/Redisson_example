package golf.example.distributedlock.domain.member.model.repository

import golf.example.distributedlock.global.security.CustomUserDetails

interface MemberCustomRepository {

    fun getDetailByMemberId(memberId: Long): CustomUserDetails?

    fun getDetailByEmail(email: String): CustomUserDetails?
}
