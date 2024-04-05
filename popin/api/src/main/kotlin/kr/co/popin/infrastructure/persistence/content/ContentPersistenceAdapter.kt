package kr.co.popin.infrastructure.persistence.content

import kr.co.popin.domain.model.content.Content
import kr.co.popin.infrastructure.persistence.content.entity.ContentEntity
import kr.co.popin.infrastructure.persistence.content.query.ContentJooqRepository
import org.locationtech.jts.geom.Point
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ContentPersistenceAdapter(
    private val contentRepository: ContentJooqRepository,
) {

    fun save(title: String, address: String, point: Point): Content {
        val id = contentRepository.generateId()
        val contentEntity = ContentEntity(id, title, address, point, LocalDateTime.now())
        contentRepository.insert(contentEntity)
        return this.toDomain(contentEntity)
    }

    private fun toDomain(entity: ContentEntity): Content {
        return Content(entity.id,
                       entity.title,
                       entity.address,
                       entity.point,
                       entity.createdDateTime)
    }

    private fun toPersistenceEntity(domain: Content): ContentEntity {
        return ContentEntity(domain.id,
                             domain.title,
                             domain.address,
                             domain.point,
                             domain.createdDateTime)
    }

}