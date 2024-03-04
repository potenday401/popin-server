package kr.co.popin.infrastructure.config.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.http.response.ErrorResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import kotlin.jvm.Throws

@Component
class AccessDeniedHandler (
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {

    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val errorResponse = ErrorResponse(ErrorResponseCode.ACCESS_DENIED)

        val result: String = objectMapper.writeValueAsString(errorResponse)

        response.apply {
            status = errorResponse.responseCode.httpStatus

            writer.write(result)
        }
    }
}