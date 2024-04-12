package kr.co.popin.application.content.dtos

data class UpdateContentCommand(
    val contentId: Long,
    val title: String?,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
)
