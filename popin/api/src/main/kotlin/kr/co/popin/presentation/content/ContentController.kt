package kr.co.popin.presentation.content

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.popin.application.content.ContentService
import kr.co.popin.application.content.dtos.GetContentQuery
import kr.co.popin.application.content.dtos.PostContentCommand
import kr.co.popin.application.content.dtos.UpdateContentCommand
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiErrorResponseCode
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiResponseCodes
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiSuccessResponseCode
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.http.enums.SuccessResponseCode
import kr.co.popin.infrastructure.http.response.SuccessResponse
import kr.co.popin.presentation.content.request.PostContentRequest
import kr.co.popin.presentation.content.request.PutContentRequest
import kr.co.popin.presentation.content.response.GetContentResponse
import kr.co.popin.presentation.content.response.PostContentResponse
import org.springframework.web.bind.annotation.*

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
    @Operation(summary = "컨텐츠 조회")
    @GetMapping
    fun getContents(
        @Parameter(description = """
            WKT(Well-Known Text) Polygon
            ex) POLYGON ((126.456 37.123, 126.789 37.234, 126.567 37.345, 126.456 37.123))
            """)
        @RequestParam("area") area: String
    ): SuccessResponse {
        val contentResponse = contentService.getWithPhoto(GetContentQuery(area))
            .map {
                GetContentResponse(contentId = it.contentId,
                                   userId = it.userId,
                                   title = it.title,
                                   address = it.address,
                                   latitude = it.point.y,
                                   longitude = it.point.x,
                                   photos = it.photos,
                                   createdAt = it.createdAt)
            }
        return SuccessResponse(responseData = contentResponse)
    }

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
    fun postContents(
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
    @Operation(summary = "컨텐츠 수정")
    @PutMapping("/{contentId}")
    fun putContent(
        @PathVariable contentId: Long,
        @RequestBody request: PutContentRequest,
    ): SuccessResponse {
        contentService.update(UpdateContentCommand(contentId = contentId,
                                                   title = request.title,
                                                   address = request.address,
                                                   latitude = request.latitude,
                                                   longitude = request.longitude))
        return SuccessResponse()
    }

}