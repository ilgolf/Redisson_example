package golf.example.distributedlock.domain.member.model.repository;

import golf.example.distributedlock.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>, MemberCustomRepository {

    fun existsByEmail(email: String): Boolean

    fun existsByNickname(nickname: String): Boolean
}