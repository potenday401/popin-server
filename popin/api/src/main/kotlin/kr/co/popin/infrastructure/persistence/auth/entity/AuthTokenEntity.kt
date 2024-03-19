package kr.co.popin.infrastructure.persistence.auth.entity

import java.time.LocalDateTime

data class AuthTokenEntity (
    val id: String,
    val userId: String,
    val token: String,
    val tokenType: String,
    val expirationTime: LocalDateTime
)