package kr.co.popin.domain.model.content

import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

data class Content(
    val id: Long,
    val userId: String,
    var title: String,
    var address: String,
    var point: Point,
    var createdAt: LocalDateTime
) {

    fun update(newTitle: String?, newAddress: String?, newPoint: Point?, newCreatedAt: LocalDateTime?) {
        this.title = newTitle ?: this.title
        this.address = newAddress ?: this.address
        this.point = newPoint ?: this.point
        this.createdAt = newCreatedAt ?: this.createdAt
    }

}
