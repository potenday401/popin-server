package kr.co.popin.infrastructure.config.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.popin.domain.model.auth.aggregate.AuthToken
import kr.co.popin.infrastructure.config.jwt.service.JwtTokenProvider
import kr.co.popin.infrastructure.config.security.authorization.RequestMatchers
import kr.co.popin.infrastructure.config.security.dto.UserPrincipal
import kr.co.popin.infrastructure.config.security.service.UserDetailsService
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import kotlin.jvm.Throws

@Component
class JwtTokenFilter (
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: UserDetailsService,
    private val securityRequestMatchers: RequestMatchers
) : OncePerRequestFilter() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val authorizationHeader = request.getHeader(AUTHORIZATION)
        val hasAccessTokenHeader = authorizationHeader?.startsWith("${AuthToken.ACCESS_TOKEN_PREFIX} ") == true

        val accessToken: String? = if (hasAccessTokenHeader) {
            authorizationHeader.removePrefix(AuthToken.ACCESS_TOKEN_PREFIX).trim()
        } else null

        if (accessToken != null) {
            val username = jwtTokenProvider.extractUsername(accessToken)

            if (jwtTokenProvider.validateToken(accessToken)) {
                val user = userDetailsService.loadUserByUsername(username) as UserPrincipal

                saveAuthentication(user)
            }
        }

        chain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return securityRequestMatchers.permitAll.any {
            it.matches(request)
        }
    }

    private fun saveAuthentication(userDetails: UserDetails) {
        val context = SecurityContextHolder.createEmptyContext().apply {
            authentication = UsernamePasswordAuthenticationToken(userDetails, userDetails.password, userDetails.authorities)
        }

        SecurityContextHolder.setContext(context)
    }
}