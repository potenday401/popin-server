package kr.co.popin.infrastructure.persistence.content.entity

import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

data class ContentEntity(
    val id: Long,
    val userId: String,
    val title: String,
    val address: String,
    val point: Point,
    val memorizedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)