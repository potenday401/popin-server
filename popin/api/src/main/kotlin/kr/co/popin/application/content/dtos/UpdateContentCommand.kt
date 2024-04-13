package kr.co.popin.application.content.dtos

import java.time.LocalDateTime

data class UpdateContentCommand(
    val contentId: Long,
    val title: String?,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val memorizedAt: LocalDateTime?
)
