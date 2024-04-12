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

    @Transactional(readOnly = true)
    fun getById(photoId: Long): Photo? {
        val photoEntity = photoRepository.findById(photoId) ?: return null
        return this.toDomain(photoEntity)
    }

    @Transactional(readOnly = true)
    fun getByContentIds(contentIds: Collection<Long>): List<Photo> {
        return photoRepository.findAllByContentIds(contentIds)
            .map { this.toDomain(it) }
    }

    @Transactional
    fun save(contentId: Long, url: String, createdAt: LocalDateTime): Photo {
        val id = photoRepository.generateId()
        val photoEntity = PhotoEntity(id, contentId, url, createdAt)
        photoRepository.insert(photoEntity)
        return this.toDomain(photoEntity)
    }

    @Transactional
    fun update(photo: Photo) {
        val photoEntity = this.toPersistenceEntity(photo)
        photoRepository.update(photoEntity)
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