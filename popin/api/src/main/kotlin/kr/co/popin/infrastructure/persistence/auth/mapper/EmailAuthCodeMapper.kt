package kr.co.popin.infrastructure.persistence.auth.mapper

import kr.co.popin.base.ModelMapper
import kr.co.popin.domain.model.auth.aggregate.EmailAuthCode
import kr.co.popin.domain.model.auth.vo.AuthCode
import kr.co.popin.domain.model.auth.vo.AuthCodeId
import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.infrastructure.persistence.auth.entity.EmailAuthCodeEntity

object EmailAuthCodeMapper : ModelMapper<EmailAuthCode, EmailAuthCodeEntity> {
    override fun mapToDomainEntity(model: EmailAuthCodeEntity): EmailAuthCode {
        return EmailAuthCode(
            id = AuthCodeId(model.id),
            userEmail = UserEmail(model.userEmail),
            code = AuthCode(model.code),
            expirationTime = model.expirationTime,
            createAt = model.createAt
        )
    }

    override fun mapToPersistenceEntity(model: EmailAuthCode): EmailAuthCodeEntity {
        return EmailAuthCodeEntity(
            id = model.id.id,
            userEmail = model.userEmail.email,
            code = model.code.code,
            expirationTime = model.expirationTime,
            createAt = model.createAt
        )
    }
}