package kr.co.popin.infrastructure.config.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.http.response.ErrorResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class ServiceHandlerExceptionResolver(
    private val objectMapper: ObjectMapper
) : HandlerExceptionResolver {
    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception
    ): ModelAndView {
        val isErrorResponse = ex.message?.startsWith(ErrorResponseCode.ERROR_CODE_PREFIX) == true

        val errorResponse = if (isErrorResponse) {
            val responseCode = ErrorResponseCode.fromCode(ex.message)
            response.status = responseCode.httpStatus

            ErrorResponse(responseCode)
        } else {
            response.status = ErrorResponseCode.UNKNOWN.httpStatus

            ErrorResponse(responseMessage = ex.message)
        }

        val result: String = objectMapper.writeValueAsString(errorResponse)
        response.writer.write(result)

        return ModelAndView()
    }
}