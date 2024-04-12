package kr.co.popin.presentation.photo

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.popin.application.photo.PhotoService
import kr.co.popin.application.photo.dtos.ChangePhotoCommand
import kr.co.popin.application.photo.dtos.UploadPhotoCommand
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiErrorResponseCode
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiResponseCodes
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiSuccessResponseCode
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.http.enums.SuccessResponseCode
import kr.co.popin.infrastructure.http.response.SuccessResponse
import kr.co.popin.presentation.photo.response.PostPhotoResponse
import kr.co.popin.presentation.photo.response.PutPhotoResponse
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
            ApiErrorResponseCode(ErrorResponseCode.BAD_REQUEST)
        ]
    )
    @Operation(summary = "사진 업로드")
    @PostMapping
    fun postPhotos(
        @RequestParam("contentId") contentId: Long,
        @RequestParam("createdAt") createdAt: LocalDateTime,
        @RequestPart("image-file") image: MultipartFile
    ): SuccessResponse {
        val photo = photoService.upload(
            UploadPhotoCommand(contentId = contentId,
                               image = image,
                               createdAt = createdAt)
        )
        val response = PostPhotoResponse(photoId = photo.id,
                                         contentId = photo.contentId,
                                         url = photo.url,
                                         createdAt = photo.createdAt)
        return SuccessResponse(responseData = response)
    }

    @ApiResponseCodes(
        success = [
            ApiSuccessResponseCode(SuccessResponseCode.SUCCESS)
        ],
        error = [
            ApiErrorResponseCode(ErrorResponseCode.ACCESS_DENIED),
            ApiErrorResponseCode(ErrorResponseCode.UNAUTHORIZED),
            ApiErrorResponseCode(ErrorResponseCode.BAD_REQUEST),
            ApiErrorResponseCode(ErrorResponseCode.NOT_FOUND_RESOURCE)
        ]
    )
    @Operation(summary = "사진 변경")
    @PutMapping("/{photoId}")
    fun putPhotos(
        @PathVariable("photoId") photoId: Long,
        @RequestParam("createdAt") createdAt: LocalDateTime,
        @RequestPart("image-file") image: MultipartFile
    ): SuccessResponse {
        val photo = photoService.change(
            ChangePhotoCommand(photoId = photoId,
                               image = image,
                               createdAt = createdAt)
        )
        val response = PutPhotoResponse(photoId = photo.id,
                                        contentId = photo.contentId,
                                        url = photo.url,
                                        createdAt = photo.createdAt)
        return SuccessResponse(responseData = response)
    }

    @ApiResponseCodes(
        success = [
            ApiSuccessResponseCode(SuccessResponseCode.SUCCESS)
        ],
        error = [
            ApiErrorResponseCode(ErrorResponseCode.ACCESS_DENIED),
            ApiErrorResponseCode(ErrorResponseCode.UNAUTHORIZED),
            ApiErrorResponseCode(ErrorResponseCode.BAD_REQUEST),
            ApiErrorResponseCode(ErrorResponseCode.NOT_FOUND_RESOURCE)
        ]
    )
    @Operation(summary = "사진 삭제")
    @DeleteMapping("/{photoId}")
    fun deletePhotos(
        @PathVariable("photoId") photoId: Long
    ): SuccessResponse {
        photoService.delete(photoId)
        return SuccessResponse()
    }

}