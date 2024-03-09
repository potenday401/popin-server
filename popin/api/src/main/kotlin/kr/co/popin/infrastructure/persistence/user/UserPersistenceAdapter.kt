package kr.co.popin.infrastructure.persistence.user

import kr.co.popin.domain.model.user.aggregate.User
import kr.co.popin.domain.model.user.persistence.IUserPersistencePort
import kr.co.popin.infrastructure.persistence.user.mapper.UserMapper
import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.infrastructure.persistence.user.query.IUserRepository
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter (
    private val userRepository: IUserRepository
) : IUserPersistencePort {
    override fun save(user: User): User {
        val userEntity = UserMapper.mapToPersistenceEntity(user)
        userRepository.insert(userEntity)

        val savedUser = userRepository.findById(userEntity.id)
            ?: throw IllegalArgumentException("test")

        return UserMapper.mapToDomainEntity(savedUser)
    }

    override fun delete(user: User) {
        userRepository.delete(
            UserMapper.mapToPersistenceEntity(user)
        )
    }

    override fun findByEmail(userEmail: UserEmail): User? {
        val userEntity = userRepository.findByEmail(userEmail.email)
            ?: return null

        return UserMapper.mapToDomainEntity(userEntity)
    }
}