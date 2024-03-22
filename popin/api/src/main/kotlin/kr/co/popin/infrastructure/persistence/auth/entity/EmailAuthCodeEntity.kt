package kr.co.popin.infrastructure.persistence.auth.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class EmailAuthCodeEntity (
    val id: String,
    val userEmail: String,
    val code: String,
    val expirationTime: LocalDateTime,
    val createAt: LocalDate
)