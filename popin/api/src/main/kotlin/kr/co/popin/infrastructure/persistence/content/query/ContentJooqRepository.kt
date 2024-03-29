package kr.co.popin.infrastructure.persistence.content.query

import kr.co.popin.infrastructure.persistence.content.entity.ContentEntity
import kr.co.popin.tables.records.JContentRecord
import kr.co.popin.tables.references.CONTENT
import org.jooq.Configuration
import org.jooq.impl.DAOImpl
import org.springframework.stereotype.Repository

@Repository
class ContentJooqRepository(
        configuration: Configuration
) : DAOImpl<JContentRecord, ContentEntity, Long>(CONTENT, ContentEntity::class.java, configuration) {

    override fun getId(`object`: ContentEntity): Long? {
        return `object`.id
    }

}