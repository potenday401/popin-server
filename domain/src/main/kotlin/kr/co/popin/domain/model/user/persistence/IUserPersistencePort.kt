package kr.co.popin.domain.model.user.persistence

import kr.co.popin.domain.model.user.aggregate.User
import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.domain.model.user.vo.UserId

interface IUserPersistencePort {
    fun save(user: User): User
    fun delete(user: User)
    fun findByEmail(userEmail: UserEmail): User?
    fun findById(userId: UserId): User?
}