package kr.co.popin.domain.model.user.persistence.dto

import java.time.LocalDateTime

data class UserDto (
    val id: String,
    val email: String,
    val password: String,
    val isVerificationRequired: Boolean,
    val registerAt: LocalDateTime
)
