package kr.co.popin.infrastructure.http.enums

import org.springframework.http.HttpStatus

enum class SuccessResponseCode (
    override val code: Int,
    override val httpStatus: Int,
    override val description: String
) : IResponseCode {
    SUCCESS(1, HttpStatus.OK.value(), "요청이 정상적으로 처리 되었습니다.");

    override fun getCodePrefix(): String {
        return SUCCESS_CODE_PREFIX
    }

    companion object {
        const val SUCCESS_CODE_PREFIX = "POPIN_STATE_"
    }
}