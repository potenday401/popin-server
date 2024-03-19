package kr.co.popin.application.auth

import kr.co.popin.application.exceptions.NotFoundAuthTokenException
import kr.co.popin.domain.model.auth.aggregate.AuthToken
import kr.co.popin.domain.model.auth.dtos.AuthTokenInfo
import kr.co.popin.domain.model.auth.enums.AuthTokenType
import kr.co.popin.domain.model.auth.persistence.IAuthTokenPersistencePort
import kr.co.popin.domain.model.auth.vo.Token
import kr.co.popin.domain.model.user.vo.UserId
import kr.co.popin.infrastructure.config.jwt.service.JwtTokenProvider
import kr.co.popin.infrastructure.config.security.dto.UserPrincipal
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService (
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authPersistenceAdapter: IAuthTokenPersistencePort
) {
    @Transactional
    fun createNewAuthentication(email: String, password: String): AuthTokenInfo {
        val userPrincipal = createUserPrincipal(email, password)
        val userId = UserId(userPrincipal.getUserId())

        val accessToken = AuthToken.newAuthToken(
            userId = userId,
            token = Token(jwtTokenProvider.generateAccessToken(userPrincipal)),
            tokenType = AuthTokenType.ACCESS
        )

        val refreshToken = AuthToken.newAuthToken(
            userId = userId,
            token = Token(jwtTokenProvider.generateRefreshToken(userPrincipal)),
            tokenType = AuthTokenType.REFRESH
        )

        val savedAccessToken = authPersistenceAdapter.save(accessToken)
        val savedRefreshToken = authPersistenceAdapter.save(refreshToken)

        return AuthTokenInfo(
            accessToken = savedAccessToken.token.token,
            refreshToken = savedRefreshToken.token.token
        )
    }

    @Transactional(readOnly = true)
    fun existCheckAuthToken(
        aToken: String,
        aTokenType: AuthTokenType
    ) {
        val userPrincipal = getUserPrincipal()
        val userId = UserId(userPrincipal.getUserId())
        val token = Token(aToken)

        authPersistenceAdapter.findByUserIdAndTokenAndTokenType(
            userId = userId,
            token = token,
            tokenType = aTokenType
        ) ?: throw NotFoundAuthTokenException()
    }

    @Transactional
    fun expireAuthTokens() {
        val userPrincipal = getUserPrincipal()
        val userId = UserId(userPrincipal.getUserId())

        authPersistenceAdapter.deleteAllByUserId(userId)
    }

    private fun createUserPrincipal(email: String, password: String): UserPrincipal {
        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                email,
                password
            )
        ).principal as UserPrincipal
    }

    private fun getUserPrincipal(): UserPrincipal {
        return SecurityContextHolder
            .getContext()
            .authentication
            .principal as UserPrincipal
    }
}