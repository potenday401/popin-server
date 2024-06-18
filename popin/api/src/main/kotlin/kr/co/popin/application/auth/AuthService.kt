package kr.co.popin.application.auth

import kr.co.popin.application.auth.dtos.EmailAuthCodeInfo
import kr.co.popin.application.exceptions.NotFoundAuthTokenException
import kr.co.popin.domain.model.auth.aggregate.AuthToken
import kr.co.popin.domain.model.auth.aggregate.EmailAuthCode
import kr.co.popin.domain.model.auth.dtos.AuthTokenInfo
import kr.co.popin.domain.model.auth.enums.AuthTokenType
import kr.co.popin.domain.model.auth.persistence.IAuthTokenPersistencePort
import kr.co.popin.domain.model.auth.vo.AuthCode
import kr.co.popin.domain.model.auth.vo.Token
import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.domain.model.user.vo.UserId
import kr.co.popin.infrastructure.config.jwt.service.JwtTokenProvider
import kr.co.popin.infrastructure.config.security.dto.UserPrincipal
import kr.co.popin.infrastructure.config.security.service.UserDetailsService
import kr.co.popin.infrastructure.persistence.auth.EmailAuthPersistenceAdapter
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class AuthService (
    private val userDetailsService: UserDetailsService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authPersistenceAdapter: IAuthTokenPersistencePort,
    private val emailAuthPersistenceAdapter: EmailAuthPersistenceAdapter
) {
    @Transactional
    fun createNewAuthentication(userPrincipal: UserPrincipal): AuthTokenInfo {
        val userId = UserId(userPrincipal.getUserId())

        val accessToken = jwtTokenProvider.generateAccessToken(userPrincipal)

        val refreshToken = AuthToken.newAuthToken(
            userId = userId,
            token = Token(jwtTokenProvider.generateRefreshToken(userPrincipal)),
            tokenType = AuthTokenType.REFRESH
        )

        val savedRefreshToken = authPersistenceAdapter.save(refreshToken)

        return AuthTokenInfo(
            accessToken = accessToken,
            refreshToken = savedRefreshToken.token.token
        )
    }

    @Transactional
    fun createNewAuthentication(email: String, password: String): AuthTokenInfo {
        val userPrincipal = createUserPrincipal(email, password)

        return this.createNewAuthentication(userPrincipal)
    }

    @Transactional(readOnly = true)
    fun existCheckAuthToken(
        aToken: String,
        aTokenType: AuthTokenType,
        aUserId: String
    ) {
        val userId = UserId(aUserId)
        val token = Token(aToken)

        authPersistenceAdapter.findByUserIdAndTokenAndTokenType(
            userId = userId,
            token = token,
            tokenType = aTokenType
        ) ?: throw NotFoundAuthTokenException()
    }

    @Transactional
    fun expireAuthTokens(aUserId: String) {
        val userId = UserId(aUserId)

        authPersistenceAdapter.deleteAllByUserId(userId)
    }

    @Transactional
    fun refresh(accessToken: String, refreshToken: String): AuthTokenInfo {
        jwtTokenProvider.validateToken(refreshToken)

        val userPrincipal = userDetailsService.loadUserByUsername(
            jwtTokenProvider.extractUsername(refreshToken)
        ) as UserPrincipal

        this.existCheckAuthToken(
            aToken = refreshToken,
            aTokenType = AuthTokenType.REFRESH,
            aUserId = userPrincipal.getUserId()
        )
        this.expireAuthTokens(userPrincipal.getUserId())

        return this.createNewAuthentication(userPrincipal)
    }

    @Transactional
    fun createEmailAuthCode(email: String): EmailAuthCodeInfo {
        val gmtZoneId = ZoneId.of("GMT")
        val currentDate = LocalDate.now(gmtZoneId)

        val userEmail = UserEmail(email)
        val emailAuthCodes = emailAuthPersistenceAdapter.findAllByUserEmailAndCreateAt(
            userEmail = userEmail,
            date = currentDate
        )
        val toDaySendCount = emailAuthCodes.count()

        this.expireEmailAuthCodes(emailAuthCodes)

        if (toDaySendCount >= EmailAuthCode.EMAIL_DAY_SEND_LIMIT) {
            return EmailAuthCodeInfo(
                toDaySendCount = toDaySendCount
            )
        }

        val savedEmailAuthCode = emailAuthPersistenceAdapter.save(
            EmailAuthCode.newEmailAuthCode(userEmail)
        )

        return EmailAuthCodeInfo(
            authCode = savedEmailAuthCode.code.code,
            toDaySendCount = toDaySendCount + 1
        )
    }

    @Transactional(readOnly = true)
    fun getEmailAuthCode(aEmail: String, aAuthCode: String): EmailAuthCode? {
        val gmtZoneId = ZoneId.of("GMT")
        val now = LocalDateTime.now(gmtZoneId)

        val notExpiredAuthCodes: List<EmailAuthCode> = emailAuthPersistenceAdapter.findAllByUserEmailAndCode(
            userEmail = UserEmail(aEmail),
            code = AuthCode(aAuthCode)
        ).filter { authCode ->
            authCode.expirationTime.isAfter(now)
        }

        return notExpiredAuthCodes
            .maxByOrNull(EmailAuthCode::createAt)
    }

    @Transactional
    fun expireEmailAuthCode(emailAuthCode: EmailAuthCode): EmailAuthCodeInfo {
        val gmtZoneId = ZoneId.of("GMT")
        val now = LocalDateTime.now(gmtZoneId)

        val expiredEmailAuthCode = EmailAuthCode(
            id = emailAuthCode.id,
            userEmail = emailAuthCode.userEmail,
            code = emailAuthCode.code,
            expirationTime = now,
            createAt = emailAuthCode.createAt
        )

        emailAuthPersistenceAdapter.update(expiredEmailAuthCode)

        val emailAuthCodes = emailAuthPersistenceAdapter.findAllByUserEmailAndCreateAt(
            userEmail = emailAuthCode.userEmail,
            date = now.toLocalDate()
        )

        return EmailAuthCodeInfo(
            toDaySendCount = emailAuthCodes.count()
        )
    }

    private fun expireEmailAuthCodes(emailAuthCodes: List<EmailAuthCode>) {
        val gmtZoneId = ZoneId.of("GMT")
        val now = LocalDateTime.now(gmtZoneId)

        val expiredEmailAuthCodes = emailAuthCodes.map { emailAuthCode ->
            EmailAuthCode(
                id = emailAuthCode.id,
                userEmail = emailAuthCode.userEmail,
                code = emailAuthCode.code,
                expirationTime = now,
                createAt = emailAuthCode.createAt
            )
        }

        emailAuthPersistenceAdapter.updateAll(expiredEmailAuthCodes)
    }

    @Transactional(readOnly = true)
    fun getUserIdByAccessToken(): String {
        return getUserPrincipal().getUserId()
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