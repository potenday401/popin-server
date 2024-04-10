package kr.co.popin.application.content.dtos

import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

data class UploadPhotoCommand(
    val contentId: Long,
    val image: MultipartFile,
    val createdDateTime: LocalDateTime
)
