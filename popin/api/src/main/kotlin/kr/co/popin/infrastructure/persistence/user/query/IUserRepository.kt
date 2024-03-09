package kr.co.popin.infrastructure.persistence.user.query

import kr.co.popin.infrastructure.persistence.user.entity.UserEntity
import kr.co.popin.tables.records.JUserRecord
import org.jooq.DAO

interface IUserRepository : DAO<JUserRecord, UserEntity, String> {
    fun findByEmail(email: String): UserEntity?
}