package golf.example.distributedlock.domain.account.application

import golf.example.distributedlock.domain.account.error.AccountException
import golf.example.distributedlock.global.jwt.TokenProvider
import golf.example.distributedlock.global.security.CustomUserDetails
import golf.example.distributedlock.global.security.CustomUserDetailsService
import golf.example.distributedlock.global.jwt.dto.TokenBaseDto
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val customUserDetailsService: CustomUserDetailsService,
    private val tokenProvider: TokenProvider,
    private val managerBuilder: AuthenticationManagerBuilder
) {

    @Transactional
    fun login(email: String, password: String): TokenBaseDto {
        val userDetails = customUserDetailsService.loadUserByEmail(email)
        val authToken = UsernamePasswordAuthenticationToken(userDetails, "")

        val authenticate = managerBuilder.`object`.authenticate(authToken)

        return tokenProvider.createToken(userDetails.memberId, authenticate)
    }
    
    fun reissue(refreshToken: String): String {
        check(tokenProvider.validateToken(refreshToken)) { throw AccountException.InvalidRefreshTokenException() }

        val authentication = tokenProvider.getAuthentication(refreshToken)
        val principal = authentication.principal as CustomUserDetails

        SecurityContextHolder.getContext().authentication = authentication
        return tokenProvider.createToken(principal.memberId, authentication).accessToken
    }
}
