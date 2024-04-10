package kr.co.popin.infrastructure.persistence.photo

import kr.co.popin.domain.model.photo.Photo
import kr.co.popin.infrastructure.persistence.photo.entity.PhotoEntity
import kr.co.popin.infrastructure.persistence.photo.query.PhotoJooqRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class PhotoPersistenceAdapter(
    private val photoRepository: PhotoJooqRepository
) {

    @Transactional
    fun save(contentId: Long, url: String, createdDateTime: LocalDateTime): Photo {
        val id = photoRepository.generateId()
        val photoEntity = PhotoEntity(id, contentId, url, createdDateTime)
        photoRepository.insert(photoEntity)
        return this.toDomain(photoEntity)
    }

    private fun toDomain(entity: PhotoEntity): Photo {
        return Photo(entity.id,
                     entity.contentId,
                     entity.url,
                     entity.createdAt)
    }

    private fun toPersistenceEntity(domain: Photo): PhotoEntity {
        return PhotoEntity(domain.id,
                           domain.contentId,
                           domain.url,
                           domain.createdAt)
    }

}