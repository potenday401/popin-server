package kr.co.popin.infrastructure.persistence.auth

import kr.co.popin.domain.model.auth.aggregate.EmailAuthCode
import kr.co.popin.domain.model.auth.persistence.IEmailAuthCodePersistencePort
import kr.co.popin.domain.model.auth.vo.AuthCode
import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.infrastructure.persistence.auth.mapper.EmailAuthCodeMapper
import kr.co.popin.infrastructure.persistence.auth.query.IEmailAuthCodeRepository
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class EmailAuthPersistenceAdapter (
    private val emailAuthCodeRepository: IEmailAuthCodeRepository
) : IEmailAuthCodePersistencePort {
    override fun save(emailAuthCode: EmailAuthCode): EmailAuthCode {
        val emailAuthCodeEntity = EmailAuthCodeMapper.mapToPersistenceEntity(emailAuthCode)
        emailAuthCodeRepository.insert(emailAuthCodeEntity)

        val savedEmailAuthCode = emailAuthCodeRepository.findById(emailAuthCodeEntity.id)
            ?: throw IllegalArgumentException("test")

        return EmailAuthCodeMapper.mapToDomainEntity(savedEmailAuthCode)
    }

    override fun update(emailAuthCode: EmailAuthCode) {
        val emailAuthCodeEntity = EmailAuthCodeMapper.mapToPersistenceEntity(emailAuthCode)
        emailAuthCodeRepository.update(emailAuthCodeEntity)
    }

    override fun updateAll(emailAuthCodes: List<EmailAuthCode>) {
        val emailAuthCodeEntities = emailAuthCodes
            .map(EmailAuthCodeMapper::mapToPersistenceEntity)
        emailAuthCodeRepository.update(emailAuthCodeEntities)
    }

    override fun findAllByUserEmailAndCode(userEmail: UserEmail, code: AuthCode): List<EmailAuthCode> {
        val authTokenEntities = emailAuthCodeRepository.findAllByUserEmailAndCode(
            userEmail = userEmail.email,
            code = code.code
        )

        return authTokenEntities
            .map(EmailAuthCodeMapper::mapToDomainEntity)
    }

    override fun findAllByUserEmailAndCreateAt(userEmail: UserEmail, date: LocalDate): List<EmailAuthCode> {
        val authTokenEntities = emailAuthCodeRepository.findAllByUserEmailAndCreateAt(
            userEmail = userEmail.email,
            date = date
        )

        return authTokenEntities
            .map(EmailAuthCodeMapper::mapToDomainEntity)
    }

}