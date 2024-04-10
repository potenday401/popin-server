package kr.co.popin.infrastructure.persistence.content

import kr.co.popin.domain.model.content.Content
import kr.co.popin.infrastructure.persistence.content.entity.ContentEntity
import kr.co.popin.infrastructure.persistence.content.query.ContentJooqRepository
import org.locationtech.jts.geom.Point
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Component
class ContentPersistenceAdapter(
    private val contentRepository: ContentJooqRepository,
) {

    @Transactional
    fun save(userId: String, title: String, address: String, point: Point): Content {
        val id = contentRepository.generateId()
        val contentEntity = ContentEntity(id, userId, title, address, point, LocalDateTime.now())
        contentRepository.insert(contentEntity)
        return this.toDomain(contentEntity)
    }

    private fun toDomain(entity: ContentEntity): Content {
        return Content(entity.id,
                       entity.userId,
                       entity.title,
                       entity.address,
                       entity.point,
                       entity.createdAt)
    }

    private fun toPersistenceEntity(domain: Content): ContentEntity {
        return ContentEntity(domain.id,
                             domain.userId,
                             domain.title,
                             domain.address,
                             domain.point,
                             domain.createdAt)
    }

}