package kr.co.popin.infrastructure.persistence.content

import kr.co.popin.domain.model.content.Content
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.persistence.content.entity.ContentEntity
import kr.co.popin.infrastructure.persistence.content.query.ContentJooqRepository
import kr.co.popin.infrastructure.persistence.content.query.ContentQueryCondition
import org.locationtech.jts.geom.Point
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Component
class ContentPersistenceAdapter(
    private val contentRepository: ContentJooqRepository,
) {

    @Transactional(readOnly = true)
    fun getById(contentId: Long): Content {
        val contentEntity = (contentRepository.findById(contentId)
            ?: throw NoSuchElementException(ErrorResponseCode.NOT_FOUND_RESOURCE.getRealCode()))
        return this.toDomain(contentEntity)
    }

    @Transactional(readOnly = true)
    fun getByQueryCondition(condition: ContentQueryCondition): List<Content> {
        return contentRepository.findByQueryCondition(condition)
            .map { this.toDomain(it) }
    }

    @Transactional
    fun save(userId: String, title: String, address: String, point: Point): Content {
        val id = contentRepository.generateId()
        val contentEntity = ContentEntity(id, userId, title, address, point, LocalDateTime.now())
        contentRepository.insert(contentEntity)
        return this.toDomain(contentEntity)
    }

    @Transactional
    fun update(content: Content) {
        contentRepository.update(this.toPersistenceEntity(content))
    }

    private fun toDomain(entity: ContentEntity): Content {
        return Content(id = entity.id,
                       userId = entity.userId,
                       title = entity.title,
                       address = entity.address,
                       point = entity.point,
                       createdAt = entity.createdAt)
    }

    private fun toPersistenceEntity(domain: Content): ContentEntity {
        return ContentEntity(id = domain.id,
                             userId = domain.userId,
                             title = domain.title,
                             address = domain.address,
                             point = domain.point,
                             createdAt = domain.createdAt)
    }

}