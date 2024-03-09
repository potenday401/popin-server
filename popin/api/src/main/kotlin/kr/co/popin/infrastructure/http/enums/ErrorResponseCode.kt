package kr.co.popin.infrastructure.http.enums

import org.springframework.http.HttpStatus

enum class ErrorResponseCode (
    override val code: Int,
    override val httpStatus: Int
) : IResponseCode {
    ACCESS_DENIED(1, HttpStatus.FORBIDDEN.value()),
    UNAUTHORIZED(2, HttpStatus.UNAUTHORIZED.value()),
    UNKNOWN(3, HttpStatus.INTERNAL_SERVER_ERROR.value()),
    INVALID_EMAIL(4, HttpStatus.BAD_REQUEST.value()),
    INVALID_PASSWORD(5, HttpStatus.BAD_REQUEST.value()),
    DUPLICATE_USER(6, HttpStatus.BAD_REQUEST.value())
    ;

    override fun getCodePrefix(): String {
        return ERROR_CODE_PREFIX
    }

    companion object {
        const val ERROR_CODE_PREFIX = "POPIN_ERROR_"

        fun fromCode(code: String?): ErrorResponseCode {
            for (enumValue in entries) {
                if (code == enumValue.getRealCode()) {
                    return enumValue
                }
            }

            return UNKNOWN
        }
    }
}