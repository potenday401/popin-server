package kr.co.popin.application.user

import kr.co.popin.application.auth.AuthService
import kr.co.popin.application.auth.dtos.EmailAuthCodeInfo
import kr.co.popin.application.exceptions.NotFoundConfirmCodeException
import kr.co.popin.application.exceptions.UserExistsException
import kr.co.popin.application.external.aws.MailSender
import kr.co.popin.application.external.aws.dtos.Mail
import kr.co.popin.application.external.aws.enums.MailType
import kr.co.popin.domain.model.auth.aggregate.AuthToken
import kr.co.popin.domain.model.auth.dtos.AuthTokenInfo
import kr.co.popin.domain.model.auth.enums.AuthTokenType
import kr.co.popin.domain.model.user.aggregate.User
import kr.co.popin.domain.model.user.persistence.IUserPersistencePort
import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.domain.model.user.vo.UserPassword
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService (
    private val passwordEncoder: PasswordEncoder,
    private val authService: AuthService,
    private val mailSender: MailSender,
    private val userPersistenceAdapter: IUserPersistencePort
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

    @Transactional
    fun login(email: String, password: String): AuthTokenInfo {
        val userEmail = UserEmail(email)
        val userPassword = UserPassword(password)

        validateEmail(userEmail)
        validatePassword(userPassword)

        return authService.createNewAuthentication(
            email = email,
            password = password
        )
    }

    @Transactional
    fun logout(accessToken: String) {
        val removedPrefixAccessToken = accessToken.removePrefix(AuthToken.ACCESS_TOKEN_PREFIX).trim()

        authService.existCheckAuthToken(
            aToken = removedPrefixAccessToken,
            aTokenType = AuthTokenType.ACCESS
        )

        authService.expireAuthTokens()
    }

    @Transactional(readOnly = true)
    fun emailDuplicateCheck(email: String) {
        val userEmail = UserEmail(email)

        validateEmail(userEmail)
        userExistsCheck(userEmail)
    }

    @Transactional
    fun sendConfirmCodeMail(email: String): EmailAuthCodeInfo {
        val userEmail = UserEmail(email)

        validateEmail(userEmail)

        val emailAuthCodeInfo = authService.createEmailAuthCode(email)

        if (emailAuthCodeInfo.authCode != null) {
            mailSender.send(
                to = email,
                mail = Mail(
                    mailType = MailType.MAIL_CONFIRM,
                    variables = mapOf("confirmCode" to emailAuthCodeInfo.authCode)
                )
            )
        }

        return emailAuthCodeInfo
    }

    @Transactional
    fun verifyConfirmCode(email: String, authCode: String): EmailAuthCodeInfo {
        val userEmail = UserEmail(email)

        validateEmail(userEmail)

        val lastNotExpiredAuthCode = authService.getEmailAuthCode(
            aEmail = email,
            aAuthCode = authCode
        ) ?: throw NotFoundConfirmCodeException()

        return authService.expireEmailAuthCode(lastNotExpiredAuthCode)
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