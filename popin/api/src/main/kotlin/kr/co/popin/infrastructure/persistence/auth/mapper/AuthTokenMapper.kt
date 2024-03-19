package kr.co.popin.infrastructure.persistence.auth.mapper

import kr.co.popin.base.ModelMapper
import kr.co.popin.domain.model.auth.aggregate.AuthToken
import kr.co.popin.domain.model.auth.enums.AuthTokenType
import kr.co.popin.domain.model.auth.vo.AuthTokenId
import kr.co.popin.domain.model.auth.vo.Token
import kr.co.popin.domain.model.user.vo.UserId
import kr.co.popin.infrastructure.persistence.auth.entity.AuthTokenEntity

object AuthTokenMapper : ModelMapper<AuthToken, AuthTokenEntity> {
    override fun mapToDomainEntity(model: AuthTokenEntity): AuthToken {
        return AuthToken(
            AuthTokenId(model.id),
            UserId(model.id),
            Token(model.token),
            AuthTokenType.valueOf(model.tokenType),
            model.expirationTime
        )
    }

    override fun mapToPersistenceEntity(model: AuthToken): AuthTokenEntity {
        return AuthTokenEntity(
            model.id.id,
            model.userId.id,
            model.token.token,
            model.tokenType.name,
            model.expirationTime
        )
    }
}