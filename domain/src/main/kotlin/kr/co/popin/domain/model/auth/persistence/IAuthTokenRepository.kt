package kr.co.popin.domain.model.auth.persistence

import kr.co.popin.domain.model.auth.persistence.dto.AuthTokenDto
import kr.co.popin.domain.model.user.vo.UserId

interface IAuthTokenRepository {
    fun findAllByUserId(userId: UserId): List<AuthTokenDto>
}