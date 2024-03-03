package kr.co.popin.domain.model.user.vo

import java.io.Serializable

data class UserEmail (
    val email: String
) : Serializable {

    init {
        if (email.length > EMAIL_LIMIT_LENGTH) {
            throw IllegalArgumentException("POPIN_000001")
        }

        if (!EMAIL_REGEX.matches(email)) {
            throw IllegalArgumentException("POPIN_000002")
        }
    }

    companion object {
        private const val serialVersionUID = 1L

        private const val EMAIL_LIMIT_LENGTH = 254

        private val EMAIL_REGEX = Regex("""^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""")

        fun newUserEmail(email: String) = UserEmail(email)
    }
}
