package kr.co.popin.domain.model.auth.vo

import java.io.Serializable

data class AuthCode(
    val code: String
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L

        private const val AUTH_CODE_LENGTH: Int = 6
        private const val AUTH_CODE_MIN_VALUE: Int = 1
        private const val AUTH_CODE_MAX_VALUE: Int = 9

        fun newAuthCode(): AuthCode {
            val code = (1.. AUTH_CODE_LENGTH)
                .map {
                    (AUTH_CODE_MIN_VALUE..AUTH_CODE_MAX_VALUE).random()
                }
                .joinToString("")

            return AuthCode(code)
        }
    }
}
