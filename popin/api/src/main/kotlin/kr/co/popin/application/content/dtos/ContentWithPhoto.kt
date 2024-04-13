package kr.co.popin.application.content.dtos

import kr.co.popin.domain.model.photo.Photo
import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

data class ContentWithPhoto(
    val contentId: Long,
    val userId: String,
    val title: String,
    val address: String,
    val point: Point,
    val photos: List<Photo>,
    val memorizedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
