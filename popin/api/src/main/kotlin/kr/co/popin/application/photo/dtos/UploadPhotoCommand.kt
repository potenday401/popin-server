package kr.co.popin.application.photo.dtos

import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

data class UploadPhotoCommand(
    val contentId: Long,
    val image: MultipartFile,
    val createdAt: LocalDateTime
)
