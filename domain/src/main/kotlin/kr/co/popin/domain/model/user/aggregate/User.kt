package kr.co.popin.domain.model.user.aggregate

import kr.co.popin.domain.model.user.vo.*
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId

data class User (
    val id: UserId,
    val email: UserEmail,
    var password: IUserPassword
) : Serializable {

    private var isVerificationRequired: Boolean
    private val registerAt: LocalDateTime

    fun emailVerify() {
        this.isVerificationRequired = true
    }

    fun hashingPassword(hashedPassword: String) {
        if (this.password is UserHashedPassword) return

        this.password = UserHashedPassword.newUserHashedPassword(hashedPassword)
    }

    init {
        val gmtZoneId = ZoneId.of("GMT")

        this.isVerificationRequired = false
        this.registerAt = LocalDateTime.now(gmtZoneId)
    }

    companion object {
        private const val serialVersionUID = 1L

        fun newUser(
            email: String,
            password: String
        ): User {
            return User(
                UserId.newUserId(),
                UserEmail.newUserEmail(email),
                UserPassword.newUserPassword(password)
            )
        }
    }
}