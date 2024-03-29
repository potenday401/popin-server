package kr.co.popin.infrastructure.persistence.content.entity

import kr.co.popin.domain.model.content.Content
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
        fun create(content: Content): ContentEntity {
            return ContentEntity(content.id, content.title, content.address, content.point, content.createdDateTime)
        }
    }

    fun toDomain(): Content {
        return Content(this.id, this.title, this.address, this.point, this.createdDateTime)
    }

}