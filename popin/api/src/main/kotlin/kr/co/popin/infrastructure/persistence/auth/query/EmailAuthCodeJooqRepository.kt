package kr.co.popin.infrastructure.persistence.auth.query

import kr.co.popin.infrastructure.persistence.auth.entity.EmailAuthCodeEntity
import kr.co.popin.tables.JEmailAuthCode
import kr.co.popin.tables.records.JEmailAuthCodeRecord
import kr.co.popin.tables.references.EMAIL_AUTH_CODE
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.impl.DAOImpl
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class EmailAuthCodeJooqRepository (
    configuration: Configuration,
    private val dslContext: DSLContext
) : IEmailAuthCodeRepository,
    DAOImpl<JEmailAuthCodeRecord, EmailAuthCodeEntity, String>(EMAIL_AUTH_CODE, EmailAuthCodeEntity::class.java, configuration)
{
    private val jEmailAuthCode = JEmailAuthCode.EMAIL_AUTH_CODE

    override fun getId(`object`: EmailAuthCodeEntity): String {
        return `object`.id
    }

    override fun findAllByUserEmailAndCode(
        userEmail: String,
        code: String
    ): List<EmailAuthCodeEntity> {
        return dslContext
            .selectFrom(jEmailAuthCode)
            .where(
                jEmailAuthCode.USER_EMAIL.eq(userEmail),
                jEmailAuthCode.CODE.eq(code)
            )
            .fetchInto(EmailAuthCodeEntity::class.java)
    }

    override fun findAllByUserEmailAndCreateAt(
        userEmail: String,
        date: LocalDate
    ): List<EmailAuthCodeEntity> {
        return dslContext
            .selectFrom(jEmailAuthCode)
            .where(
                jEmailAuthCode.USER_EMAIL.eq(userEmail),
                jEmailAuthCode.CREATE_AT.eq(date)
            )
            .fetchInto(EmailAuthCodeEntity::class.java)
    }
}