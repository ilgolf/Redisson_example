package golf.example.distributedlock.domain.account.error

import golf.example.distributedlock.global.exception.error.BusinessException
import golf.example.distributedlock.global.exception.error.ErrorCode

sealed class AccountException {

    class InvalidRefreshTokenException : BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)
}