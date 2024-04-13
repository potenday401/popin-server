package kr.co.popin.presentation.content.request

import java.time.LocalDateTime

data class PostContentRequest(
    val title: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val createdAt: LocalDateTime,
)
