package kr.co.popin.infrastructure.config.jwt.property

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.ZoneId

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties (
    val secret: String,
    val zoneId: ZoneId
)