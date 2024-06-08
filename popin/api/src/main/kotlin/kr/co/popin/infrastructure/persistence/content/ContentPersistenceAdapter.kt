package kr.co.popin.infrastructure.persistence.content

import kr.co.popin.domain.model.content.Content
import kr.co.popin.domain.model.user.vo.UserId
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
    fun getById(contentId: Long): Content? {
        val contentEntity = contentRepository.findById(contentId) ?: return null
        return this.toDomain(contentEntity)
    }

    @Transactional(readOnly = true)
    fun findIdsByUserId(userId: UserId): List<Long> {
        return contentRepository.findIdsByUserId(userId.id)
    }

    @Transactional(readOnly = true)
    fun getByQueryCondition(condition: ContentQueryCondition): List<Content> {
        return contentRepository.findByQueryCondition(condition)
            .map { this.toDomain(it) }
    }

    @Transactional
    fun save(userId: String, title: String, address: String, point: Point, memorizedAt: LocalDateTime): Content {
        val id = contentRepository.generateId()
        val now = LocalDateTime.now()
        val contentEntity = ContentEntity(id = id,
                                          userId = userId,
                                          title = title,
                                          address = address,
                                          point = point,
                                          memorizedAt = memorizedAt,
                                          createdAt = now,
                                          updatedAt = now)
        contentRepository.insert(contentEntity)
        return this.toDomain(contentEntity)
    }

    @Transactional
    fun update(content: Content) {
        val contentEntity = this.toPersistenceEntity(content)
        contentRepository.update(contentEntity)
    }

    @Transactional
    fun delete(content: Content) {
        val contentEntity = this.toPersistenceEntity(content)
        contentRepository.delete(contentEntity)
    }

    @Transactional
    fun deleteAllByUserId(userId: UserId) {
        contentRepository.deleteAllByUserId(userId.id)
    }

    private fun toDomain(entity: ContentEntity): Content {
        return Content(id = entity.id,
                       userId = entity.userId,
                       title = entity.title,
                       address = entity.address,
                       point = entity.point,
                       memorizedAt = entity.memorizedAt,
                       createdAt = entity.createdAt,
                       updatedAt = entity.updatedAt)
    }

    private fun toPersistenceEntity(domain: Content): ContentEntity {
        return ContentEntity(id = domain.id,
                             userId = domain.userId,
                             title = domain.title,
                             address = domain.address,
                             point = domain.point,
                             memorizedAt = domain.memorizedAt,
                             createdAt = domain.createdAt,
                             updatedAt = domain.updatedAt)
    }

}