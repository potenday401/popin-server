package kr.co.popin.application.user

import kr.co.popin.application.exceptions.UserExistsException
import kr.co.popin.domain.model.user.aggregate.User
import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.domain.model.user.vo.UserPassword
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.persistence.user.UserPersistenceAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService (
    private val passwordEncoder: PasswordEncoder,
    private val userPersistenceAdapter: UserPersistenceAdapter
) {
    @Transactional
    fun registerUser(email: String, password: String) {
        val userEmail = UserEmail(email)
        val userPassword = UserPassword(password)

        validateEmail(userEmail)
        validatePassword(userPassword)
        userExistsCheck(userEmail)

        val newUser = User.newUser(
            userEmail,
            hashPassword(userPassword)
        )

        userPersistenceAdapter.save(newUser)
    }

    @Transactional(readOnly = true)
    fun emailDuplicateCheck(email: String) {
        val userEmail = UserEmail(email)

        validateEmail(userEmail)
        userExistsCheck(userEmail)
    }

    private fun hashPassword(userPassword: UserPassword): UserPassword {
        val encodedPassword = passwordEncoder.encode(userPassword.password)

        return UserPassword(encodedPassword)
    }

    private fun validateEmail(userEmail: UserEmail) {
        if (!EMAIL_REGEX.matches(userEmail.email)) {
            throw IllegalArgumentException(ErrorResponseCode.INVALID_EMAIL.getRealCode())
        }
    }

    private fun validatePassword(userPassword: UserPassword) {
        if (!PASSWORD_REGEX.matches(userPassword.password)) {
            throw IllegalArgumentException(ErrorResponseCode.INVALID_PASSWORD.getRealCode())
        }
    }

    private fun userExistsCheck(userEmail: UserEmail) {
        val userEntity = userPersistenceAdapter.findByEmail(userEmail)

        if (userEntity != null) {
            throw UserExistsException()
        }
    }

    companion object {
        private val EMAIL_REGEX = "^(?=.{4,254}\$)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
        private val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d).*[A-Za-z\\d~`!@#\$%^&*()_\\-+={\\[}\\]|\\\\:;\"'<,>.?/]{8,20}$".toRegex()
    }
}