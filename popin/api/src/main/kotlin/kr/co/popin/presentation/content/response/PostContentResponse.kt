package kr.co.popin.presentation.content.response

import java.time.LocalDateTime

data class PostContentResponse(
    val contentId: Long,
    val userId: String,
    val title: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val createdAt: LocalDateTime
)
