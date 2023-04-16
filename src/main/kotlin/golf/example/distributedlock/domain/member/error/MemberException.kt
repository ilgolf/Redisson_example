package golf.example.distributedlock.domain.member.error

import golf.example.distributedlock.global.exception.error.BusinessException
import golf.example.distributedlock.global.exception.error.ErrorCode

sealed class MemberException {

    class DuplicateEmailException(email: String) : BusinessException(ErrorCode.DUPLICATE_EMAIL, email)
    class DuplicateNicknameException(nickname: String) : BusinessException(ErrorCode.DUPLICATE_NICKNAME, nickname)
    class MemberNotFoundException(memberId: Long) : BusinessException(ErrorCode.MEMBER_NOT_FOUND, memberId.toString()) {
        constructor(): this(-1)
    }
}
