package kr.co.popin.presentation.content

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.popin.application.content.ContentService
import kr.co.popin.application.content.dtos.PostContentCommand
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiErrorResponseCode
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiResponseCodes
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiSuccessResponseCode
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.http.enums.SuccessResponseCode
import kr.co.popin.infrastructure.http.response.SuccessResponse
import kr.co.popin.presentation.content.request.PostContentRequest
import kr.co.popin.presentation.content.response.PostContentResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Content")
@RequestMapping("/contents")
@RestController
class ContentController(
    private val contentService: ContentService
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
    @Operation(summary = "컨텐츠 등록")
    @PostMapping
    fun postContent(
        @RequestBody request: PostContentRequest
    ): SuccessResponse {
        val content = contentService.post(PostContentCommand(title = request.title,
                                                             address = request.address,
                                                             latitude = request.latitude,
                                                             longitude = request.longitude))
        val response = PostContentResponse(contentId = content.id,
                                           userId = content.userId,
                                           title = content.title,
                                           address = content.address,
                                           longitude = content.point.y,
                                           latitude = content.point.x,
                                           createdAt = content.createdAt)
        return SuccessResponse(responseData = response)
    }

}