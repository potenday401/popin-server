package kr.co.popin.presentation.content.request

import java.time.LocalDateTime

data class PostPhotoRequest(
    val contentId: Long,
    val createdDateTime: LocalDateTime
)
