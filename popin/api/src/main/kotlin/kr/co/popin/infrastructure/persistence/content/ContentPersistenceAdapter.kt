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
        val contentEntity = this.toPersistenceEntity(content)
        contentRepository.insert(contentEntity)
        return this.toDomain(contentEntity)
    }

    private fun toDomain(entity: ContentEntity): Content {
        return Content.create(entity.title,
                              entity.address,
                              entity.point,
                              entity.createdDateTime)
    }

    private fun toPersistenceEntity(domain: Content): ContentEntity {
        return ContentEntity.create(domain.title,
                                    domain.address,
                                    domain.point,
                                    domain.createdDateTime)
    }

}