package kr.co.popin.presentation.photo

import kr.co.popin.application.content.dtos.UploadPhotoCommand
import kr.co.popin.application.photo.PhotoService
import kr.co.popin.infrastructure.http.response.SuccessResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@RequestMapping("/photos")
@RestController
class PhotoController(
    private val photoService: PhotoService
) {

    @PostMapping
    fun postPhoto(
        @RequestParam("contentId") contentId: Long,
        @RequestParam("createdDateTime") createdDateTime: String,
        @RequestPart("image-file") image: MultipartFile
    ): SuccessResponse {
        val format = LocalDateTime.parse(createdDateTime)
        photoService.upload(UploadPhotoCommand(contentId,
                                               image,
                                               format))
        return SuccessResponse()
    }

}