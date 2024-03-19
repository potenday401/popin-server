package kr.co.popin.domain.model.user.aggregate

import kr.co.popin.domain.model.user.vo.*
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId

data class User (
    val id: UserId,
    val email: UserEmail,
    val password: UserPassword,
    val registerAt: LocalDateTime
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L

        fun newUser(
            email: UserEmail,
            password: UserPassword
        ): User {
            val gmtZoneId = ZoneId.of("GMT")

            return User(
                UserId.newUserId(),
                email,
                password,
                LocalDateTime.now(gmtZoneId)
            )
        }
    }
}