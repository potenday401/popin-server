package kr.co.popin.infrastructure.persistence.content.entity

import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

class ContentEntity private constructor(
        val id: Long?,
        val title: String,
        val address: String,
        val point: Point,
        val createdDateTime: LocalDateTime
) {

    companion object {
        fun create(title: String, address: String, point: Point, createdDateTime: LocalDateTime): ContentEntity {
            return ContentEntity(null, title, address, point, createdDateTime)
        }
    }


}