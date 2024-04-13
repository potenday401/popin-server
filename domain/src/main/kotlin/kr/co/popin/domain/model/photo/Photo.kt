package kr.co.popin.domain.model.photo

import java.time.LocalDateTime

data class Photo(
    val id: Long,
    val contentId: Long,
    var url: String,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
) {

    fun change(newUrl: String, updatedAt: LocalDateTime) {
        this.url = newUrl
        this.updatedAt = updatedAt
    }

}
