package kr.co.popin.presentation.content.response

import kr.co.popin.domain.model.photo.Photo
import java.time.LocalDateTime

data class GetContentResponse(
    val contentId: Long,
    val userId: String,
    val title: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val photos: List<Photo>,
    val memorizedAt: LocalDateTime
)