package kr.co.popin.infrastructure.persistence.auth.query

import kr.co.popin.domain.model.auth.enums.AuthTokenType
import kr.co.popin.infrastructure.persistence.auth.entity.AuthTokenEntity
import kr.co.popin.tables.records.JAuthTokenRecord
import org.jooq.DAO

interface IAuthTokenRepository : DAO<JAuthTokenRecord, AuthTokenEntity, String> {
    fun findByUserIdAndTokenAndTokenType(userId: String, token: String, tokenType: String): AuthTokenEntity?
}