package kr.co.popin.infrastructure.config.docs.springdoc

import io.swagger.v3.core.converter.ModelConverters
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import kr.co.popin.infrastructure.config.docs.springdoc.annotations.ApiResponseCodes
import kr.co.popin.infrastructure.http.enums.IResponseCode
import kr.co.popin.infrastructure.http.enums.SuccessResponseCode
import kr.co.popin.infrastructure.http.response.ErrorResponse
import kr.co.popin.infrastructure.http.response.SuccessResponse
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.method.HandlerMethod
import java.lang.reflect.Type

@Configuration
class OperationCustomizerWithEnums : OperationCustomizer {
    override fun customize(operation: Operation, handlerMethod: HandlerMethod): Operation {
        val apiResponses = operation.responses.apply {
            clear()
        }
        val type: Type = handlerMethod.returnType.genericParameterType

        val apiResponseCodes = handlerMethod.getMethodAnnotation(ApiResponseCodes::class.java)
            ?: return operation

        apiResponseCodes.apply {
            success.forEach {
                putApiResponseCode(
                    apiResponses = apiResponses,
                    responseCode = it.value,
                    type = type
                )
            }

            error.forEach {
                putApiResponseCode(
                    apiResponses = apiResponses,
                    responseCode = it.value,
                    type = type
                )
            }
        }

        return operation
    }

    private fun putApiResponseCode(
        apiResponses: ApiResponses,
        responseCode: IResponseCode,
        type: Type
    ) {
        apiResponses[responseCode.getRealCode()] = getApiResponse(responseCode, type)
    }

    private fun getApiResponse(
        responseCode: IResponseCode,
        type: Type
    ): ApiResponse {
        val schema = getSchema(
            responseCode = responseCode,
            type = type
        )

        val exampleResponse = if (responseCode is SuccessResponseCode) {
            SuccessResponse()
        } else {
            ErrorResponse()
        }
        val example = Example()
            .value(exampleResponse)

        val mediaType = MediaType()
            .schema(schema)
            .addExamples(
                responseCode.getRealCode(),
                example
            )

        val content = Content()
            .addMediaType(
                APPLICATION_JSON_VALUE,
                mediaType
            )

        return ApiResponse()
            .content(content)
            .description(responseCode.description)
    }

    private fun getSchema(
        responseCode: IResponseCode,
        type: Type
    ): Schema<*> {
        val schema = ModelConverters
            .getInstance()
            .readAllAsResolvedSchema(type)
            .schema

        val properties = schema.properties

        properties["success"]?.setDefault(responseCode is SuccessResponseCode)
        properties["status"]?.setDefault(responseCode.httpStatus)
        properties["code"]?.setDefault(responseCode.getRealCode())
        properties["message"]?.setDefault(responseCode.description)

        return schema
    }
}