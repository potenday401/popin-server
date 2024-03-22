package kr.co.popin.domain.model.auth.aggregate

import kr.co.popin.domain.model.auth.vo.AuthCode
import kr.co.popin.domain.model.auth.vo.AuthCodeId
import kr.co.popin.domain.model.user.vo.UserEmail
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

data class EmailAuthCode(
    val id: AuthCodeId,
    val userEmail: UserEmail,
    val code: AuthCode,
    val expirationTime: LocalDateTime,
    val createAt: LocalDate
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L

        private const val EMAIL_EXPIRED_TIME: Long = 10
        const val EMAIL_DAY_SEND_LIMIT: Int = 5

        fun newEmailAuthCode(
            userEmail: UserEmail
        ): EmailAuthCode {
            val gmtZoneId = ZoneId.of("GMT")
            val now = LocalDateTime.now(gmtZoneId)

            return EmailAuthCode(
                id = AuthCodeId.newAuthCodeId(),
                userEmail = userEmail,
                code = AuthCode.newAuthCode(),
                expirationTime = now.plusMinutes(EMAIL_EXPIRED_TIME),
                createAt = now.toLocalDate()
            )
        }
    }
}

