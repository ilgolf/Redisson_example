package golf.example.distributedlock.global.config

import golf.example.distributedlock.global.jwt.TokenProvider
import golf.example.distributedlock.global.jwt.error.JwtAccessDeniedHandler
import golf.example.distributedlock.global.jwt.error.JwtAuthenticationEntryPoint
import golf.example.distributedlock.global.security.CustomAuthenticationProvider
import golf.example.distributedlock.global.security.CustomUserDetailsService
import golf.example.distributedlock.global.jwt.JwtSecurityConfig
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val customUserDetailsService: CustomUserDetailsService,
    private val tokenProvider: TokenProvider,
    private val authenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val accessDeniedHandler: JwtAccessDeniedHandler
) {

    companion object {
        private const val PUBLIC_POINT = "/api/v2/public/**"
    }

    @Bean
    fun customAuthenticationProvider(): CustomAuthenticationProvider? {
        return CustomAuthenticationProvider()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()

            .formLogin().disable()

            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .antMatchers(PUBLIC_POINT).permitAll()
            .antMatchers("/favicon.ico").permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/error/**").permitAll()
            .antMatchers(HttpMethod.POST, "/tickets").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .userDetailsService(customUserDetailsService)
            .apply(JwtSecurityConfig(tokenProvider))
            .and()
            .build()
    }
}