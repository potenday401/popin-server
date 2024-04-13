package kr.co.popin.presentation.photo.response

import java.time.LocalDateTime

data class PostPhotoResponse(
    val photoId: Long,
    val contentId: Long,
    val url: String,
    val memorizedAt: LocalDateTime,
)
