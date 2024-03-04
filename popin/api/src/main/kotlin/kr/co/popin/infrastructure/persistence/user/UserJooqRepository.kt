package kr.co.popin.infrastructure.persistence.user

import kr.co.popin.domain.model.user.persistence.IUserRepository
import kr.co.popin.domain.model.user.persistence.dto.UserDto
import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.tables.JUser
import org.springframework.stereotype.Repository
import org.jooq.DSLContext

@Repository
class UserJooqRepository (
    private val dslContext: DSLContext
) : IUserRepository {

    private val user = JUser.USER

    override fun findByEmail(email: UserEmail): UserDto? {
        return dslContext
            .select()
            .from(user)
            .where(user.EMAIL.eq(email.email))
            .fetchOneInto(UserDto::class.java)
    }
}