package golf.example.distributedlock.domain.member.model

import org.hibernate.annotations.Where
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import javax.persistence.*

@Where(clause = "deleted = false")
@Entity
class Member (

    @Column(length = 100, nullable = false)
    var email: String,

    @Column(length = 200, nullable = false)
    var password: String,

    @Column(length = 20, nullable = false)
    var name: String,

    @Column(length = 20, unique = true, nullable = false)
    var nickname: String,

    var birth: LocalDate,

    @Column(length = 20, nullable = false)
    var phoneNumber: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    var memberId: Long = 0

    @Column(nullable = false)
    var deleted: Boolean = false

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var roleType: RoleType = RoleType.USER

    // 비즈니스 로직
    fun updateMember(nickname: String, name: String): Member {
        this.nickname = nickname
        this.name = name

        return this
    }

    fun matchPassword(passwordEncoder: PasswordEncoder, rawPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, this.password)
    }

    fun encodePassword(passwordEncoder: PasswordEncoder): Member {
        this.password = passwordEncoder.encode(this.password)
        return this
    }

    fun delete(): Member {
        this.deleted = true
        return this
    }

    // equalsAndHashcode
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        if (memberId != other.memberId) return false

        return true
    }

    override fun hashCode(): Int {
        return memberId.hashCode()
    }
}
