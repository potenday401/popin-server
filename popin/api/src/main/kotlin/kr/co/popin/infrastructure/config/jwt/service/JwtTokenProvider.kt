package kr.co.popin.infrastructure.config.jwt.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import kr.co.popin.infrastructure.config.jwt.property.JwtProperties
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.jvm.Throws

@Component
class JwtTokenProvider (
    private val jwtProperties: JwtProperties
) {
    private val secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtProperties.secret))

    fun generateAccessToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = mapOf("authorities" to userDetails.authorities)

        return createToken(
            claims,
            userDetails.username,
            jwtProperties.accessTokenExpirationTimeInMillis
        )
    }

    fun generateRefreshToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = mapOf("authorities" to userDetails.authorities)

        return createToken(
            claims,
            userDetails.username,
            jwtProperties.refreshTokenExpirationTimeInMillis
        )
    }

    @Throws(JwtException::class)
    fun validateToken(token: String): Boolean {
        Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)

        return true
    }

    fun extractUsername(token: String): String {
        return extractClaim(
            token,
            Claims::getSubject
        )
    }

    fun extractExpiration(token: String): LocalDateTime {
        val expirationDate = extractClaim(
            token,
            Claims::getExpiration
        )

        return expirationDate
            .toInstant()
            .atZone(jwtProperties.zoneId)
            .toLocalDateTime()
    }

    fun <T> extractClaim(token: String, claimsResolver: Function1<Claims, T>): T {
        val claims = extractAllClaims(token)

        return claimsResolver.invoke(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun isTokenExpired(token: String): Boolean = try {
        extractExpiration(token).isBefore(LocalDateTime.now())
    } catch (e: ExpiredJwtException) {
        true
    }

    private fun createToken(claims: Map<String, Any>, subject: String, expirationTime: Long): String {
        val now = LocalDateTime.now()
        val expirationDateTime = now.plus(expirationTime, ChronoUnit.MILLIS)
        val issuedAt = Date.from(now.atZone(jwtProperties.zoneId).toInstant())
        val expiration = Date.from(expirationDateTime.atZone(jwtProperties.zoneId).toInstant())

        return Jwts.builder()
            .claims(claims)
            .subject(subject)
            .issuedAt(issuedAt)
            .expiration(expiration)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }
}