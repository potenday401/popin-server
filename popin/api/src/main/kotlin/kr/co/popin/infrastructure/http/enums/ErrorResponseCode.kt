package kr.co.popin.infrastructure.http.enums

import org.springframework.http.HttpStatus

enum class ErrorResponseCode (
    override val code: Int,
    override val httpStatus: Int,
    override val description: String
) : IResponseCode {
    ACCESS_DENIED(1, HttpStatus.FORBIDDEN.value(), "권한이 부족합니다."),
    UNAUTHORIZED(2, HttpStatus.UNAUTHORIZED.value(), "인증되지 않은 요청입니다."),
    UNKNOWN(3, HttpStatus.INTERNAL_SERVER_ERROR.value(), "요청하신 서버에 문제가 발생했습니다."),
    INVALID_EMAIL(4, HttpStatus.BAD_REQUEST.value(), "이메일이 잘못 되었습니다."),
    INVALID_PASSWORD(5, HttpStatus.BAD_REQUEST.value(), "비밀번호가 잘못 되었습니다."),
    DUPLICATE_USER(6, HttpStatus.BAD_REQUEST.value(), "유저가 이미 존재합니다."),
    BAD_REQUEST(7, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 요청입니다."),
    NOT_FOUND_RESOURCE(8, HttpStatus.NOT_FOUND.value(), "찾을 수 없는 데이터입니다."),
    DUPLICATE_PASSWORD(9, HttpStatus.BAD_REQUEST.value(), "동일한 비밀번호로 변경할 수 없습니다."),
    NOT_MATCHED_PASSWORD(10, HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다.")
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