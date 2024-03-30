package kr.co.popin.infrastructure.persistence.content

import kr.co.popin.domain.model.content.Content
import kr.co.popin.infrastructure.persistence.content.entity.ContentEntity
import kr.co.popin.infrastructure.persistence.content.query.ContentJooqRepository
import org.springframework.stereotype.Component

@Component
class ContentPersistenceAdapter(
        private val contentRepository: ContentJooqRepository,
) {

    fun save(content: Content): Content {
        val contentEntity = ContentEntity.create(content.title, content.address, content.point, content.createdDateTime)
        contentRepository.insert(contentEntity)
        return this.toDomain(contentEntity)
    }

    private fun toDomain(entity: ContentEntity): Content {
        return Content.create(entity.title,
                              entity.address,
                              entity.point,
                              entity.createdDateTime)
    }

}