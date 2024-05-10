package kr.co.popin.application.content.dtos

import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

data class PostContentWithPhotoCommand(
    val title: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val memorizedAt: LocalDateTime,
    val photos: List<MultipartFile>,
)
