package kr.co.popin.domain.model.user.vo

import java.io.Serializable

interface IUserPassword { val password: String }

data class UserPassword (
    override val password: String
) : IUserPassword, Serializable {

    init {
        if (password.length !in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH) {
            throw IllegalArgumentException("POPIN_000003")
        }

        if (PASSWORD_REGEX.matches(password)) {
            throw IllegalArgumentException("POPIN_000004")
        }
    }

    companion object {
        private const val serialVersionUID = 1L

        private const val PASSWORD_MIN_LENGTH = 8
        private const val PASSWORD_MAX_LENGTH = 20

        private val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d).*[A-Za-z\\d~`!@#\$%^&*()_\\-+={\\[}\\]|\\\\:;\"'<,>.?/]$".toRegex()

        fun newUserPassword(password: String): UserPassword = UserPassword(password)
    }
}

data class UserHashedPassword (
    override val password: String
) : IUserPassword, Serializable {

    companion object {
        private const val serialVersionUID = 1L

        fun newUserHashedPassword(password: String): UserHashedPassword = UserHashedPassword(password)
    }
}