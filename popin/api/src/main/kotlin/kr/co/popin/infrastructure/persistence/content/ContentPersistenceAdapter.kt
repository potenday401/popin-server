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
        val contentEntity = ContentEntity.create(content)
        contentRepository.insert(contentEntity)
        return contentEntity.toDomain()
    }

}