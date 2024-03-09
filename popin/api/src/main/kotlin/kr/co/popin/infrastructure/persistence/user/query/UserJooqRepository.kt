package kr.co.popin.infrastructure.persistence.user.query

import kr.co.popin.infrastructure.persistence.user.entity.UserEntity
import kr.co.popin.tables.JUser
import kr.co.popin.tables.records.JUserRecord
import kr.co.popin.tables.references.USER
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.impl.DAOImpl
import org.springframework.stereotype.Repository


@Repository
class UserJooqRepository (
    configuration: Configuration,
    private val dslContext: DSLContext
) : IUserRepository,
    DAOImpl<JUserRecord, UserEntity, String>(USER, UserEntity::class.java, configuration)
{
    private val jUser = JUser.USER

    override fun getId(`object`: UserEntity): String {
        return `object`.id
    }

    override fun findByEmail(email: String): UserEntity? {
        return dslContext
            .selectFrom(jUser)
            .where(jUser.EMAIL.eq(email))
            .fetchOneInto(UserEntity::class.java)
    }
}