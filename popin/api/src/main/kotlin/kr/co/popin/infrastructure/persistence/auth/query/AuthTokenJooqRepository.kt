package kr.co.popin.infrastructure.persistence.auth.query

import kr.co.popin.infrastructure.persistence.auth.entity.AuthTokenEntity
import kr.co.popin.tables.JAuthToken
import kr.co.popin.tables.records.JAuthTokenRecord
import kr.co.popin.tables.references.AUTH_TOKEN
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.impl.DAOImpl
import org.springframework.stereotype.Repository

@Repository
class AuthTokenJooqRepository (
    configuration: Configuration,
    private val dslContext: DSLContext
) : IAuthTokenRepository,
    DAOImpl<JAuthTokenRecord, AuthTokenEntity, String>(AUTH_TOKEN, AuthTokenEntity::class.java, configuration)
{
    private val jAuthToken = JAuthToken.AUTH_TOKEN

    override fun getId(`object`: AuthTokenEntity): String {
        return `object`.id
    }

    override fun findByUserIdAndTokenAndTokenType(
        userId: String,
        token: String,
        tokenType: String
    ): AuthTokenEntity? {
        return dslContext
            .selectFrom(jAuthToken)
            .where(
                jAuthToken.USER_ID.eq(userId),
                jAuthToken.TOKEN.eq(token),
                jAuthToken.TOKEN_TYPE.eq(tokenType)
            )
            .fetchOneInto(AuthTokenEntity::class.java)
    }
}