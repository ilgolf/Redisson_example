package golf.example.distributedlock.domain.member.application

import golf.example.distributedlock.domain.member.model.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberCommandService(
    private val memberRepository: MemberRepository,
    private val encoder: PasswordEncoder
) {

    @Transactional
    fun save(saveDto: golf.example.distributedlock.domain.member.dto.request.MemberSaveRequestDto): Long {
        this.validateDuplicationEmail(saveDto.email)
        this.validateDuplicationNickname(saveDto.nickname)

        val member = saveDto.toMemberEntity().encodePassword(encoder)

        return memberRepository.save(member).memberId
    }

    @Transactional
    fun update(requestDto: golf.example.distributedlock.domain.member.dto.request.MemberUpdateRequestDto, memberId: Long) {
        val member = getMember(memberId)

        if (member.nickname != requestDto.nickname) {
            validateDuplicationNickname(requestDto.nickname)
        }

        member.updateMember(requestDto.nickname, requestDto.name)
    }

    @Transactional
    fun delete(memberId: Long) {
        getMember(memberId).delete()
    }


    private fun validateDuplicationNickname(nickname: String) {
        check(!memberRepository.existsByNickname(nickname)) { throw golf.example.distributedlock.domain.member.error.MemberException.DuplicateNicknameException(nickname) }
    }

    private fun validateDuplicationEmail(email: String) {
        check(!memberRepository.existsByEmail(email)) { throw golf.example.distributedlock.domain.member.error.MemberException.DuplicateEmailException(email) }
    }

    private fun getMember(memberId: Long) =
        memberRepository.findByIdOrNull(memberId)?: throw golf.example.distributedlock.domain.member.error.MemberException.MemberNotFoundException(memberId)
}

