package golf.example.distributedlock.domain.member.dto.request

import java.time.LocalDate

data class MemberUpdateRequestDto(
    var name: String,
    var nickname: String,
    var birthday: LocalDate
)
