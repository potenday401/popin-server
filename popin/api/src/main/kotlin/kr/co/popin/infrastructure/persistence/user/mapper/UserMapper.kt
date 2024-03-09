package kr.co.popin.infrastructure.persistence.user.mapper

import kr.co.popin.base.ModelMapper
import kr.co.popin.domain.model.user.aggregate.User
import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.domain.model.user.vo.UserId
import kr.co.popin.domain.model.user.vo.UserPassword
import kr.co.popin.infrastructure.persistence.user.entity.UserEntity

object UserMapper : ModelMapper<User, UserEntity> {
    override fun mapToDomainEntity(model: UserEntity): User {
        return User(
            UserId(model.id),
            UserEmail(model.email),
            UserPassword(model.password),
            model.registerAt
        )
    }

    override fun mapToPersistenceEntity(model: User): UserEntity {
        return UserEntity(
            model.id.id,
            model.email.email,
            model.password.password,
            model.registerAt
        )
    }
}