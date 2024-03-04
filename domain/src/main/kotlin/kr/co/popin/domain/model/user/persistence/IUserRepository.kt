package kr.co.popin.domain.model.user.persistence

import kr.co.popin.domain.model.user.persistence.dto.UserDto
import kr.co.popin.domain.model.user.vo.UserEmail

interface IUserRepository {
    fun findByEmail(email: UserEmail): UserDto?
}