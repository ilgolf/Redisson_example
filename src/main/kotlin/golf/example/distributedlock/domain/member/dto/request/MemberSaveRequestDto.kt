package golf.example.distributedlock.domain.member.dto.request

import golf.example.distributedlock.domain.member.model.Member
import java.time.LocalDate

data class MemberSaveRequestDto(
    var email: String,
    var password: String,
    var name: String,
    var nickname: String,
    var birth: LocalDate,
    var phoneNumber: String
) {

    fun toMemberEntity(): Member {
        return Member(
            email = email,
            password = password,
            name = name,
            nickname = nickname,
            birth = birth,
            phoneNumber = phoneNumber
        )
    }
}
