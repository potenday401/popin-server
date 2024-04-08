package kr.co.popin.infrastructure.persistence.photo.entity

import java.time.LocalDateTime

data class PhotoEntity(
    val id: Long,
    val contentId: Long,
    val url: String,
    val createdAt: LocalDateTime
)