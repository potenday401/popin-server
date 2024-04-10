package kr.co.popin.domain.model.content

import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

data class Content(
    val id: Long,
    val userId: String,
    val title: String,
    val address: String,
    val point: Point,
    val createdAt: LocalDateTime
)
