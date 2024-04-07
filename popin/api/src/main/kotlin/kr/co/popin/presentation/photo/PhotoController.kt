package kr.co.popin.presentation.photo

import kr.co.popin.application.content.dtos.UploadPhotoCommand
import kr.co.popin.application.photo.PhotoService
import kr.co.popin.infrastructure.http.response.SuccessResponse
import kr.co.popin.presentation.content.request.PostPhotoRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/photos")
@RestController
class PhotoController(
    private val photoService: PhotoService
) {

    @PostMapping
    fun postPhoto(
        @RequestPart("image-info") request: PostPhotoRequest,
        @RequestPart("image-file") image: MultipartFile
    ): SuccessResponse {
        photoService.upload(UploadPhotoCommand(request.contentId,
                                               image,
                                               request.createdDateTime))
        return SuccessResponse()
    }

}