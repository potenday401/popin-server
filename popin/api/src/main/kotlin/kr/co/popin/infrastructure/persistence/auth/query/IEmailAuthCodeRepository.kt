package kr.co.popin.infrastructure.persistence.auth.query

import kr.co.popin.infrastructure.persistence.auth.entity.EmailAuthCodeEntity
import kr.co.popin.tables.records.JEmailAuthCodeRecord
import org.jooq.DAO
import java.time.LocalDate

interface IEmailAuthCodeRepository : DAO<JEmailAuthCodeRecord, EmailAuthCodeEntity, String> {
    fun findAllByUserEmailAndCode(userEmail: String, code: String): List<EmailAuthCodeEntity>
    fun findAllByUserEmailAndCreateAt(userEmail: String, date: LocalDate): List<EmailAuthCodeEntity>
}