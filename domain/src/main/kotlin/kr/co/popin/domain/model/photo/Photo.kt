package kr.co.popin.domain.model.photo

import java.time.LocalDateTime

data class Photo(
    val id: Long,
    val contentId: Long,
    var url: String,
    var createdAt: LocalDateTime
) {

    fun change(newUrl: String, createdAt: LocalDateTime) {
        this.url = newUrl
        this.createdAt = createdAt
    }

}
