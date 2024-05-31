package kr.co.popin.application.user

import kr.co.popin.application.exceptions.NotFoundUserException
import kr.co.popin.domain.model.auth.persistence.IAuthTokenPersistencePort
import kr.co.popin.domain.model.user.persistence.IUserPersistencePort
import kr.co.popin.domain.model.user.vo.UserId
import kr.co.popin.infrastructure.persistence.content.ContentPersistenceAdapter
import kr.co.popin.infrastructure.persistence.photo.PhotoPersistenceAdapter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WithdrawalUserService (
    private val authTokenPersistenceAdapter: IAuthTokenPersistencePort,
    private val userPersistenceAdapter: IUserPersistencePort,
    private val contentPersistenceAdapter: ContentPersistenceAdapter,
    private val photoPersistenceAdapter: PhotoPersistenceAdapter
) {
    @Transactional
    fun withdrawal(aUserId: String) {
        val userId = UserId(aUserId)

        val user = userPersistenceAdapter.findById(userId)
            ?: throw NotFoundUserException()

        authTokenPersistenceAdapter.deleteAllByUserId(user.id)

        val contentIds = contentPersistenceAdapter.findContentIdByUserId(user.id)
        photoPersistenceAdapter.deleteAllByContentIdIn(contentIds)

        contentPersistenceAdapter.deleteAllByUserId(user.id)

        userPersistenceAdapter.delete(user)
    }
}