package kr.co.popin.infrastructure.persistence.content.entity

import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

class ContentEntity(
    val id: Long,
    val title: String,
    val address: String,
    val point: Point,
    val createdDateTime: LocalDateTime
)