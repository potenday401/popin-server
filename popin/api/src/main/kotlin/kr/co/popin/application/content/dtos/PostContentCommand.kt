package kr.co.popin.application.content.dtos

data class PostContentCommand(
    val userId: String,
    val title: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
)
