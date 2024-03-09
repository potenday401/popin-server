package kr.co.popin.infrastructure.http.enums

import org.springframework.http.HttpStatus

enum class ErrorResponseCode (
    override val code: Int,
    override val httpStatus: Int
) : IResponseCode {
    ACCESS_DENIED(1, HttpStatus.FORBIDDEN.value()),
    UNAUTHORIZED(2, HttpStatus.UNAUTHORIZED.value()),
    UNKNOWN(3, HttpStatus.INTERNAL_SERVER_ERROR.value());

    override fun getCodePrefix(): String {
        return "POPIN_ERROR"
    }
}