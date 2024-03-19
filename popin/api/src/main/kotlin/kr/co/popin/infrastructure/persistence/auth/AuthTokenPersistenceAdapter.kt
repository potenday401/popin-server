package kr.co.popin.infrastructure.persistence.auth

import kr.co.popin.domain.model.auth.aggregate.AuthToken
import kr.co.popin.domain.model.auth.enums.AuthTokenType
import kr.co.popin.domain.model.auth.persistence.IAuthTokenPersistencePort
import kr.co.popin.domain.model.auth.vo.Token
import kr.co.popin.domain.model.user.vo.UserId
import kr.co.popin.infrastructure.persistence.auth.mapper.AuthTokenMapper
import kr.co.popin.infrastructure.persistence.auth.query.IAuthTokenRepository
import org.springframework.stereotype.Component

@Component
class AuthTokenPersistenceAdapter (
    private val authTokenRepository: IAuthTokenRepository
) : IAuthTokenPersistencePort {
    override fun save(authToken: AuthToken): AuthToken {
        val authTokenEntity = AuthTokenMapper.mapToPersistenceEntity(authToken)
        authTokenRepository.insert(authTokenEntity)

        val savedAuthToken = authTokenRepository.findById(authTokenEntity.id)
            ?: throw IllegalArgumentException("test")

        return AuthTokenMapper.mapToDomainEntity(savedAuthToken)
    }

    override fun delete(authToken: AuthToken) {
        authTokenRepository.delete(
            AuthTokenMapper.mapToPersistenceEntity(authToken)
        )
    }

    override fun deleteAllByUserId(userId: UserId) {
        authTokenRepository.deleteAllByUserId(userId.id)
    }

    override fun findByUserIdAndTokenAndTokenType(userId: UserId, token: Token, tokenType: AuthTokenType): AuthToken? {
        val authTokenEntity = authTokenRepository.findByUserIdAndTokenAndTokenType(
            userId = userId.id,
            token = token.token,
            tokenType = tokenType.name
        ) ?: return null

        return AuthTokenMapper.mapToDomainEntity(authTokenEntity)
    }
}