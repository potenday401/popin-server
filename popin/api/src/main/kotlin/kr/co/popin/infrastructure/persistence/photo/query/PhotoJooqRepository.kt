package kr.co.popin.infrastructure.persistence.photo.query

import kr.co.popin.infrastructure.persistence.content.entity.ContentEntity
import kr.co.popin.infrastructure.persistence.photo.entity.PhotoEntity
import kr.co.popin.tables.records.JPhotoRecord
import kr.co.popin.tables.references.CONTENT
import kr.co.popin.tables.references.PHOTO
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.impl.DAOImpl
import org.springframework.stereotype.Repository

@Repository
class PhotoJooqRepository(
    configuration: Configuration,
    private val dslContext: DSLContext
) : DAOImpl<JPhotoRecord, PhotoEntity, Long>(PHOTO, PhotoEntity::class.java, configuration) {

    override fun getId(`object`: PhotoEntity): Long? {
        return `object`.id
    }

    fun generateId(): Long {
        return dslContext.select()
            .from(CONTENT)
            .orderBy(CONTENT.ID.desc())
            .fetchOneInto(ContentEntity::class.java)
            ?.let { it.id + 1 }
            ?: 1
    }

}