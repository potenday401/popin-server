package kr.co.popin.presentation.content.request

data class PutContentRequest(
    val title: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
)
