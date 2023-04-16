package golf.example.distributedlock.domain.member.model.repository

import golf.example.distributedlock.global.security.CustomUserDetails
import com.querydsl.jpa.impl.JPAQueryFactory
import golf.example.distributedlock.domain.member.model.QMember.member
import golf.example.distributedlock.global.security.QCustomUserDetails
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepository(
    private val query: JPAQueryFactory
): MemberCustomRepository {

    override fun getDetailByMemberId(memberId: Long): CustomUserDetails? {
        return query.select(
            QCustomUserDetails(
                member.memberId,
                member.email,
                member.roleType)
        )
            .from(member)
            .where(member.memberId.eq(memberId))
            .fetchFirst()
    }

    override fun getDetailByEmail(email: String): CustomUserDetails? {
        return query.select(
            QCustomUserDetails(
                member.memberId,
                member.email,
                member.roleType
            )
        )
            .from(member)
            .where(member.email.eq(email))
            .fetchFirst()
    }
}