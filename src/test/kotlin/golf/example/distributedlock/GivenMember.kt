package golf.example.distributedlock

import golf.example.distributedlock.domain.member.model.Member
import java.time.LocalDate

object GivenMember {
    private const val email = "ilgolc@naver.com"
    private const val password = "1234"
    private const val name = "노경태"
    private const val nickname = "nokt"
    private val birth: LocalDate = LocalDate.of(1996, 10, 25)
    private const val phoneNumber = "010-1234-5678"


    fun toMember() =
        Member(
            email = email,
            password = password,
            name = name,
            nickname = nickname,
            birth = birth,
            phoneNumber = phoneNumber
        )
}