package kr.co.popin.infrastructure.http.enums

import com.fasterxml.jackson.annotation.JsonValue

interface IResponseCode {
    val code: Int
    val httpStatus: Int
    val description: String

    fun getCodePrefix(): String

    @JsonValue
    fun getRealCode(): String {
        val zerofillCode = String.format("%06d", this.code)

        return "${this.getCodePrefix()}${zerofillCode}"
    }
}