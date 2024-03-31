package kr.co.popin.domain.model.content

import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

data class Content(
        val id: Long?,
        val title: String,
        val address: String,
        val point: Point,
        val createdDateTime: LocalDateTime
) {

    companion object {
        fun create(title: String, address: String, point: Point, createdDateTime: LocalDateTime): Content {
            return Content(null, title, address, point, createdDateTime)
        }
    }

}
