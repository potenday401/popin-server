package kr.co.popin.domain.model.auth.persistence

import kr.co.popin.domain.model.auth.aggregate.AuthToken
import kr.co.popin.domain.model.auth.enums.AuthTokenType
import kr.co.popin.domain.model.auth.vo.Token
import kr.co.popin.domain.model.user.vo.UserId

interface IAuthTokenPersistencePort {
    fun save(authToken: AuthToken): AuthToken
    fun delete(authToken: AuthToken)
    fun findByUserIdAndTokenAndTokenType(userId: UserId, token: Token, tokenType: AuthTokenType): AuthToken?
}