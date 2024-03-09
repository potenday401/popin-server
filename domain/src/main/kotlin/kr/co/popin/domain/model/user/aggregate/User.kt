package kr.co.popin.domain.model.user.aggregate

import kr.co.popin.domain.model.user.vo.*
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId

data class User (
    val id: UserId,
    val email: UserEmail,
    val password: UserPassword
) : Serializable {

    var registerAt: LocalDateTime

    constructor(
        id: UserId,
        email: UserEmail,
        password: UserPassword,
        registerAt: LocalDateTime
    ) : this(id, email, password) {
        this.registerAt = registerAt
    }

    init {
        val gmtZoneId = ZoneId.of("GMT")

        this.registerAt = LocalDateTime.now(gmtZoneId)
    }

    companion object {
        private const val serialVersionUID = 1L

        fun newUser(
            email: UserEmail,
            password: UserPassword
        ): User {
            return User(
                UserId.newUserId(),
                email,
                password
            )
        }
    }
}