package kr.co.popin.infrastructure.persistence.photo.entity

import java.time.LocalDateTime

class PhotoEntity(
    val id: Long,
    val contentId: Long,
    val url: String,
    val createdDateTime: LocalDateTime
)