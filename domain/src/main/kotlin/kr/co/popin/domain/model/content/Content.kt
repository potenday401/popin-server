package kr.co.popin.domain.model.content

import org.locationtech.jts.geom.Point
import java.time.LocalDateTime

data class Content(
    val id: Long,
    val userId: String,
    var title: String,
    var address: String,
    var point: Point,
    var memorizedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
) {

    fun update(newTitle: String?, newAddress: String?, newPoint: Point?, newMemorizedAt: LocalDateTime?) {
        this.title = newTitle ?: this.title
        this.address = newAddress ?: this.address
        this.point = newPoint ?: this.point
        this.memorizedAt = newMemorizedAt ?: this.memorizedAt
        this.updatedAt = LocalDateTime.now()
    }

}
