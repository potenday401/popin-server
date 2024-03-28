package kr.co.popin.infrastructure.config.docs.springdoc

import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiResponseCodes
import kr.co.popin.infrastructure.config.docs.springdoc.response.SpringdocExampleResponse
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.http.enums.IResponseCode
import kr.co.popin.infrastructure.http.enums.SuccessResponseCode
import kr.co.popin.infrastructure.http.response.ErrorResponse
import kr.co.popin.infrastructure.http.response.SuccessResponse
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.method.HandlerMethod

@Configuration
class OperationCustomizerWithEnums : OperationCustomizer {
    override fun customize(operation: Operation, handlerMethod: HandlerMethod): Operation {
        val apiResponseCodes = handlerMethod.getMethodAnnotation(ApiResponseCodes::class.java)

        apiResponseCodes?.apply {
            addResponseExamples(
                operation,
                success.map { it.value }.toTypedArray()
            )
            addResponseExamples(
                operation,
                error.map { it.value }.toTypedArray()
            )
        }

        return operation
    }

    private fun addResponseExamples(
        operation: Operation,
        responseCodes: Array<out IResponseCode>
    ) {
        val apiResponses = operation.responses

        val exampleTargetResponses = responseCodes
            .map {
                when (it) {
                    is SuccessResponseCode -> SuccessResponse(it)
                    is ErrorResponseCode -> ErrorResponse(it)
                    else -> throw Error("springdoc example create failed")
                }
            }
            .apply {
                addLast(ErrorResponse(ErrorResponseCode.UNKNOWN))
            }

            val codeToResponses = exampleTargetResponses
                .groupBy { it.responseCode.httpStatus }

        codeToResponses
            .forEach { (httpStatusCode, responses) ->
                val mediaType = MediaType()
                responses
                    .forEach { response ->
                        mediaType
                            .addExamples(
                                response.responseCode.getRealCode(),
                                Example()
                                    .value(SpringdocExampleResponse(response.responseCode.getRealCode()))
                                    .description(response.responseCode.description)
                            )
                    }

                val content = Content()
                    .addMediaType(APPLICATION_JSON_VALUE, mediaType)

                val apiResponse = ApiResponse()
                    .content(content)

                apiResponses.addApiResponse(httpStatusCode.toString(), apiResponse)
            }
    }

}