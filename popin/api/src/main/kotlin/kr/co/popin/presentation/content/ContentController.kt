package kr.co.popin.presentation.content

import kr.co.popin.application.content.ContentService
import kr.co.popin.application.content.dtos.PostContentCommand
import kr.co.popin.infrastructure.http.response.SuccessResponse
import kr.co.popin.presentation.content.request.PostContentRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/contents")
@RestController
class ContentController(
    private val contentService: ContentService
) {

    @PostMapping
    fun postContent(userId: String, @RequestBody request: PostContentRequest): SuccessResponse {
        contentService.post(PostContentCommand(userId,
                                               request.title,
                                               request.address,
                                               request.latitude,
                                               request.longitude))
        return SuccessResponse()
    }

}