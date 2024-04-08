package kr.co.popin.domain.model.photo

import java.time.LocalDateTime

data class Photo(
    val id: Long,
    val contentId: Long,
    val url: String,
    val createdAt: LocalDateTime
)
