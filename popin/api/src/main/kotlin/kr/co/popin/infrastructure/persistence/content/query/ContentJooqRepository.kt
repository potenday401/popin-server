package kr.co.popin.infrastructure.persistence.content.query

import kr.co.popin.infrastructure.persistence.content.entity.ContentEntity
import kr.co.popin.tables.records.JContentRecord
import kr.co.popin.tables.references.CONTENT
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.impl.DAOImpl
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.time.ZoneOffset

@Repository
class ContentJooqRepository(
    configuration: Configuration,
    private val dslContext: DSLContext
) : DAOImpl<JContentRecord, ContentEntity, Long>(CONTENT, ContentEntity::class.java, configuration) {

    override fun getId(`object`: ContentEntity): Long? {
        return `object`.id
    }

    fun generateId(): Long {
        return dslContext.select(CONTENT.ID)
            .from(CONTENT)
            .orderBy(CONTENT.ID.desc())
            .limit(1)
            .fetchOneInto(Long::class.java)
            ?.let { it + 1 }
            ?: 1
    }

    override fun insert(entity: ContentEntity) {
        dslContext.insertInto(CONTENT)
            .columns(CONTENT.ID,
                     CONTENT.USER_ID,
                     CONTENT.TITLE,
                     CONTENT.ADDRESS,
                     DSL.field("point"),
                     CONTENT.CREATED_AT)
            .values(entity.id,
                    entity.userId,
                    entity.title,
                    entity.address,
                    DSL.field("ST_GeomFromText('${entity.point.toText()}', 4326)"),
                    entity.createdAt.atOffset(ZoneOffset.UTC))
            .execute()
    }
}