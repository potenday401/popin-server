package kr.co.popin.presentation.photo

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.popin.application.content.dtos.UploadPhotoCommand
import kr.co.popin.application.photo.PhotoService
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiErrorResponseCode
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiResponseCodes
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiSuccessResponseCode
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.http.enums.SuccessResponseCode
import kr.co.popin.infrastructure.http.response.SuccessResponse
import kr.co.popin.presentation.photo.response.PostPhotoResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Tag(name = "Photo")
@RequestMapping("/photos")
@RestController
class PhotoController(
    private val photoService: PhotoService
) {

    @ApiResponseCodes(
        success = [
            ApiSuccessResponseCode(SuccessResponseCode.SUCCESS)
        ],
        error = [
            ApiErrorResponseCode(ErrorResponseCode.ACCESS_DENIED),
            ApiErrorResponseCode(ErrorResponseCode.UNAUTHORIZED),
            ApiErrorResponseCode(ErrorResponseCode.BAD_REQUEST),
            ApiErrorResponseCode(ErrorResponseCode.UNKNOWN)
        ]
    )
    @Operation(summary = "사진 업로드")
    @PostMapping
    fun postPhoto(
        @RequestParam("contentId") contentId: Long,
        @RequestParam("createdDateTime") createdDateTime: LocalDateTime,
        @RequestPart("image-file") image: MultipartFile
    ): SuccessResponse {
        val photo = photoService.upload(UploadPhotoCommand(contentId,
                                                           image,
                                                           createdDateTime))
        val response = PostPhotoResponse(photo.id, photo.contentId, photo.url, photo.createdAt)
        return SuccessResponse(responseData = response)
    }

}