package kr.co.popin.domain.model.auth.persistence

import kr.co.popin.domain.model.auth.aggregate.EmailAuthCode
import kr.co.popin.domain.model.auth.vo.AuthCode
import kr.co.popin.domain.model.user.vo.UserEmail
import java.time.LocalDate

interface IEmailAuthCodePersistencePort {
    fun save(emailAuthCode: EmailAuthCode): EmailAuthCode
    fun update(emailAuthCode: EmailAuthCode)
    fun updateAll(emailAuthCodes: List<EmailAuthCode>)
    fun findAllByUserEmailAndCode(userEmail: UserEmail, code: AuthCode): List<EmailAuthCode>
    fun findAllByUserEmailAndCreateAt(userEmail: UserEmail, date: LocalDate): List<EmailAuthCode>
}