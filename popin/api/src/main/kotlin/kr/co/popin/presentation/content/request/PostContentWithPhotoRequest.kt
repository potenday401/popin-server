package kr.co.popin.presentation.content.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

class PostContentWithPhotoRequest(
    @field:Schema(description = "제목", example = "오늘의 풍경")
    val title: String,
    @field:Schema(description = "주소", example = "서울시 강남구")
    val address: String,
    @field:Schema(description = "위도", type = "double", example = "37.12345")
    val latitude: Double,
    @field:Schema(description = "경도", type = "double", example = "127.56789")
    val longitude: Double,
    @field:Schema(description = "등록 일시", example = "2024-04-27T15:30:00")
    val memorizedAt: LocalDateTime,
)
