package kr.co.popin.application.content.dtos

import java.time.LocalDateTime

data class PostContentCommand(
    val title: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val createdAt: LocalDateTime,
)
