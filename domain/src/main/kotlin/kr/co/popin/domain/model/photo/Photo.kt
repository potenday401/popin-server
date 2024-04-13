package kr.co.popin.domain.model.photo

import java.time.LocalDateTime

data class Photo(
    val id: Long,
    val contentId: Long,
    var url: String,
    var memorizedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
) {

    fun change(newUrl: String, newMemorizedAt: LocalDateTime) {
        this.url = newUrl
        this.memorizedAt = newMemorizedAt
        this.updatedAt = LocalDateTime.now()
    }

}
