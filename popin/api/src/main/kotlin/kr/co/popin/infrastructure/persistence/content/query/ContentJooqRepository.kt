package kr.co.popin.infrastructure.persistence.content.query

import kr.co.popin.infrastructure.persistence.content.entity.ContentEntity
import kr.co.popin.tables.records.JContentRecord
import kr.co.popin.tables.references.CONTENT
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.impl.DAOImpl
import org.jooq.impl.DSL
import org.locationtech.jts.geom.Point
import org.locationtech.jts.io.WKTReader
import org.springframework.stereotype.Repository

@Repository
class ContentJooqRepository(
    configuration: Configuration,
    private val dslContext: DSLContext,
    private val wktReader: WKTReader,
) : DAOImpl<JContentRecord, ContentEntity, Long>(CONTENT, ContentEntity::class.java, configuration) {

    override fun getId(`object`: ContentEntity): Long? {
        return `object`.id
    }

    fun generateId(): Long {
        return dslContext.select(CONTENT.ID)
            .from(CONTENT)
            .orderBy(CONTENT.ID.desc())
            .limit(1)
            .forUpdate()
            .fetchOneInto(Long::class.java)
            ?.let { it + 1 }
            ?: 1
    }

    override fun findById(id: Long?): ContentEntity? {
        return dslContext.selectFrom(CONTENT)
            .where(CONTENT.ID.eq(id))
            .fetchOne()
            ?.let { this.toEntity(it) }
    }

    fun findContentIdByUserId(userId: String): List<Long> {
        return dslContext
            .select(CONTENT.ID)
            .from(CONTENT)
            .where(CONTENT.USER_ID.eq(userId))
            .fetchInto(Long::class.java)
    }

    fun findByQueryCondition(condition: ContentQueryCondition): List<ContentEntity> {
        val contains = DSL.condition("ST_Contains(ST_GeomFromText('${condition.area.toText()}', 4326), point)")
        return dslContext.selectFrom(CONTENT)
            .where(CONTENT.USER_ID.eq(condition.userId),
                   contains)
            .fetch()
            .map { this.toEntity(it) }
    }


    override fun insert(entity: ContentEntity) {
        dslContext.insertInto(CONTENT)
            .columns(CONTENT.ID,
                     CONTENT.USER_ID,
                     CONTENT.TITLE,
                     CONTENT.ADDRESS,
                     DSL.field("point"),
                     CONTENT.MEMORIZED_AT,
                     CONTENT.CREATED_AT,
                     CONTENT.UPDATED_AT)
            .values(entity.id,
                    entity.userId,
                    entity.title,
                    entity.address,
                    DSL.field("ST_GeomFromText('${entity.point.toText()}', 4326)"),
                    entity.memorizedAt,
                    entity.createdAt,
                    entity.updatedAt)
            .execute()
    }

    override fun update(entity: ContentEntity) {
        dslContext.update(CONTENT)
            .set(CONTENT.TITLE, entity.title)
            .set(CONTENT.ADDRESS, entity.address)
            .set(DSL.field("point"), DSL.field("ST_GeomFromText('${entity.point.toText()}', 4326)") as Any)
            .set(CONTENT.MEMORIZED_AT, entity.memorizedAt)
            .set(CONTENT.UPDATED_AT, entity.updatedAt)
            .where(CONTENT.ID.eq(entity.id))
            .execute()
    }

    fun deleteAllByUserId(userId: String) {
        dslContext
            .delete(CONTENT)
            .where(CONTENT.USER_ID.eq(userId))
            .execute()
    }

    private fun toEntity(record: JContentRecord): ContentEntity {
        val wktString = record.point.toString()
            .replaceFirst("SRID=\\d+;".toRegex(), "")
            .trim()
        val point = wktReader.read(wktString) as Point
        return ContentEntity(
            id = record.id ?: throw IllegalArgumentException(),
            userId = record.userId ?: throw IllegalArgumentException(),
            title = record.title ?: throw IllegalArgumentException(),
            address = record.address ?: throw IllegalArgumentException(),
            point = point,
            memorizedAt = record.memorizedAt ?: throw IllegalArgumentException(),
            createdAt = record.createdAt ?: throw IllegalArgumentException(),
            updatedAt = record.updatedAt ?: throw IllegalArgumentException(),
        )
    }

}