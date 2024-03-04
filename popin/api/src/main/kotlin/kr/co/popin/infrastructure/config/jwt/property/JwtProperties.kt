package kr.co.popin.infrastructure.config.jwt.property

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.ZoneId

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties (
    val secret: String,
    private val accessTokenExpirationMinute: Long,
    private val refreshTokenExpirationDays: Long,
    val zoneId: ZoneId
) {

    val accessTokenExpirationTimeInMillis: Long = accessTokenExpirationMinute * ONE_MINUTE * ONE_SECOND
    val refreshTokenExpirationTimeInMillis: Long = refreshTokenExpirationDays * ONE_DAY * ONE_HOUR * ONE_MINUTE * ONE_SECOND

    fun getAccessTokenExpirationSeconds(): Long = accessTokenExpirationTimeInMillis / ONE_SECOND
    fun getRefreshTokenExpirationSeconds(): Long = refreshTokenExpirationTimeInMillis / ONE_SECOND

    fun getAccessTokenExpirationTimeInMinutes(): Long = accessTokenExpirationMinute
    fun getRefreshTokenExpirationMinutes(): Long = refreshTokenExpirationDays * ONE_DAY * ONE_HOUR

    companion object {
        const val ONE_DAY: Long = 24
        const val ONE_HOUR: Long = 60
        const val ONE_MINUTE: Long = 60
        const val ONE_SECOND: Long = 1000

        const val ACCESS_AUTH_PREFIX = "Bearer"
    }
}