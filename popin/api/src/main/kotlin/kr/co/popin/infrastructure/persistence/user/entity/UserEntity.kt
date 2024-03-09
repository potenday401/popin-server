package kr.co.popin.infrastructure.persistence.user.entity

import java.time.LocalDateTime

data class UserEntity (
    val id: String,
    val email: String,
    val password: String,
    val registerAt: LocalDateTime
)