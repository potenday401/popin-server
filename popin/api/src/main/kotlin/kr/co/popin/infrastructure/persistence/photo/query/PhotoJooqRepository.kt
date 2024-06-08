package kr.co.popin.infrastructure.persistence.photo.query

import kr.co.popin.infrastructure.persistence.photo.entity.PhotoEntity
import kr.co.popin.tables.records.JPhotoRecord
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
        return dslContext.select(PHOTO.ID)
            .from(PHOTO)
            .orderBy(PHOTO.ID.desc())
            .limit(1)
            .forUpdate()
            .fetchOneInto(Long::class.java)
            ?.let { it + 1 }
            ?: 1
    }

    fun findAllByContentIds(contentIds: Collection<Long>): List<PhotoEntity> {
        return dslContext.select(PHOTO)
            .from(PHOTO)
            .where(PHOTO.CONTENT_ID.`in`(contentIds))
            .fetchInto(PhotoEntity::class.java)
    }

    fun deleteAllByContentIdIn(contentIds: List<Long>) {
        dslContext
            .delete(PHOTO)
            .where(PHOTO.CONTENT_ID.`in`(contentIds))
            .execute()
    }

}