package kr.co.popin.infrastructure.http.enums

import com.fasterxml.jackson.annotation.JsonValue

interface IResponseCode {
    val code: Int
    val httpStatus: Int

    fun getCodePrefix(): String

    @JsonValue
    fun getRealCode(): String {
        val zerofillLength = 6 - this.code.toString().length
        val zerofillPrefix = String.format("%0" + zerofillLength + "d", 0)

        return "${this.getCodePrefix()}_${zerofillPrefix}${this.code}"
    }
}