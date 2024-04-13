package kr.co.popin.presentation.photo.response

import java.time.LocalDateTime

data class PutPhotoResponse(
    val photoId: Long,
    val contentId: Long,
    val url: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
