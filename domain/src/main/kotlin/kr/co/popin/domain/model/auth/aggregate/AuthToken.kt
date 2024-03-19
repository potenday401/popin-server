package kr.co.popin.domain.model.auth.aggregate

import kr.co.popin.domain.model.auth.enums.AuthTokenType
import kr.co.popin.domain.model.auth.vo.AuthTokenId
import kr.co.popin.domain.model.auth.vo.Token
import kr.co.popin.domain.model.user.vo.UserId
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

data class AuthToken (
    val id: AuthTokenId,
    val userId: UserId,
    val token: Token,
    val tokenType: AuthTokenType,
    val expirationTime: LocalDateTime
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L

        const val ACCESS_TOKEN_PREFIX = "Bearer"

        private const val ONE_DAY: Long = 24
        private const val ONE_HOUR: Long = 60
        private const val ONE_MINUTE: Long = 60
        private const val ONE_SECOND: Long = 1000

        private const val ACCESS_TOKEN_EXPIRATION_MIN: Long = 120
        const val ACCESS_TOKEN_EXPIRATION_MILLIS: Long = ACCESS_TOKEN_EXPIRATION_MIN * ONE_MINUTE * ONE_SECOND

        private const val REFRESH_TOKEN_EXPIRATION_DAY: Long = 14
        const val REFRESH_TOKEN_EXPIRATION_MILLIS: Long = REFRESH_TOKEN_EXPIRATION_DAY * ONE_DAY * ONE_HOUR * ONE_MINUTE * ONE_SECOND

        fun newAuthToken(
            userId: UserId,
            token: Token,
            tokenType: AuthTokenType
        ): AuthToken {
            val expirationMillis = when(tokenType) {
                AuthTokenType.ACCESS -> ACCESS_TOKEN_EXPIRATION_MILLIS
                AuthTokenType.REFRESH -> REFRESH_TOKEN_EXPIRATION_MILLIS
            }

            val gmtZoneId = ZoneId.of("GMT")

            return AuthToken(
                id = AuthTokenId.newAuthTokenId(),
                userId = userId,
                token = token,
                tokenType = tokenType,
                expirationTime = LocalDateTime.now(gmtZoneId).plus(expirationMillis, ChronoUnit.MILLIS)
            )
        }
    }
}
