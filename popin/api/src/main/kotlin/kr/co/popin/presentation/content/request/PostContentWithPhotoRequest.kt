package kr.co.popin.presentation.content.request

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile
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
    @field:Schema(description = "등록 일시", type = "string", example = "2024-04-27T10:00:00")
    val memorizedAt: LocalDateTime,
    @field:Schema(description = "사진 목록")
    val photos: List<MultipartFile>,
)
