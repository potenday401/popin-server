package kr.co.popin.application.user

import kr.co.popin.application.exceptions.NotFoundUserException
import kr.co.popin.domain.model.user.persistence.IUserPersistencePort
import kr.co.popin.domain.model.user.vo.UserId
import kr.co.popin.infrastructure.persistence.auth.AuthTokenPersistenceAdapter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WithdrawalUserService (
    private val userPersistenceAdapter: IUserPersistencePort,
    private val authTokenPersistenceAdapter: AuthTokenPersistenceAdapter
) {
    @Transactional
    fun withdrawal(aUserId: String) {
        val userId = UserId(aUserId)

        val user = userPersistenceAdapter.findById(userId)
            ?: throw NotFoundUserException()

        authTokenPersistenceAdapter.deleteAllByUserId(user.id)
        // TODO content, photo 지울 것인지 확인 필요

        userPersistenceAdapter.delete(user)
    }
}