package kr.co.popin.application.content

import kr.co.popin.application.auth.AuthService
import kr.co.popin.application.content.dtos.*
import kr.co.popin.application.photo.PhotoService
import kr.co.popin.domain.model.content.Content
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import kr.co.popin.infrastructure.persistence.content.ContentPersistenceAdapter
import kr.co.popin.infrastructure.persistence.content.query.ContentQueryCondition
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.Polygon
import org.locationtech.jts.io.WKTReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContentService(
    private val authService: AuthService,
    private val photoService: PhotoService,
    private val contentPersistenceAdapter: ContentPersistenceAdapter,
    private val geometryFactory: GeometryFactory
) {

    @Transactional(readOnly = true)
    fun getWithPhoto(contentQuery: GetContentQuery): List<ContentWithPhoto> {
        // TODO: MethodArgumentResolver 이용해 컨트롤러단에서 바로 userId 생성 예정
        val userId = authService.getUserIdByAccessToken()
        val area = WKTReader(geometryFactory).read(contentQuery.area)
        if (area !is Polygon) {
            throw IllegalArgumentException(ErrorResponseCode.BAD_REQUEST.getRealCode())
        }

        val condition = ContentQueryCondition(userId = userId, area = area)
        val contents: List<Content> = contentPersistenceAdapter.getByQueryCondition(condition)
        val contentIds = contents.map { it.id }
        val photoMap = photoService.getByContentIds(contentIds)
            .groupBy { it.contentId }

        return contents.map { content ->
            ContentWithPhoto(contentId = content.id,
                             userId = content.userId,
                             title = content.title,
                             address = content.address,
                             point = content.point,
                             photos = photoMap.getOrDefault(content.id, emptyList()),
                             memorizedAt = content.memorizedAt,
                             createdAt = content.createdAt,
                             updatedAt = content.memorizedAt)
        }
    }

    @Transactional
    fun post(command: PostContentCommand): Content {
        // TODO: MethodArgumentResolver 이용해 컨트롤러단에서 바로 userId 생성 예정
        val userId = authService.getUserIdByAccessToken()
        val coordinate = Coordinate(command.longitude, command.latitude)
        val point = geometryFactory.createPoint(coordinate)
        return contentPersistenceAdapter.save(userId = userId,
                                              title = command.title,
                                              address = command.address,
                                              point = point,
                                              memorizedAt = command.memorizedAt)
    }

    @Transactional
    fun postWithPhoto(command: PostContentWithPhotoCommand): ContentWithPhoto {
        // TODO: MethodArgumentResolver 이용해 컨트롤러단에서 바로 userId 생성 예정
        val userId = authService.getUserIdByAccessToken()
        val coordinate = Coordinate(command.longitude, command.latitude)
        val point = geometryFactory.createPoint(coordinate)

        val content = contentPersistenceAdapter.save(userId = userId,
                                                     title = command.title,
                                                     address = command.address,
                                                     point = point,
                                                     memorizedAt = command.memorizedAt)
        val photos = command.photos.map { photoService.upload(content.id, it) }

        return ContentWithPhoto(contentId = content.id,
                                userId = content.userId,
                                title = content.title,
                                address = content.address,
                                point = content.point,
                                photos = photos,
                                memorizedAt = content.memorizedAt,
                                createdAt = content.createdAt,
                                updatedAt = content.memorizedAt)
    }

    @Transactional
    fun update(command: UpdateContentCommand) {
        // TODO: MethodArgumentResolver 이용해 컨트롤러단에서 바로 userId 생성 예정
        val userId = authService.getUserIdByAccessToken()
        val content = contentPersistenceAdapter.getById(command.contentId)
            ?: throw NoSuchElementException(ErrorResponseCode.NOT_FOUND_RESOURCE.getRealCode())
        if (content.userId != userId) {
            throw IllegalArgumentException(ErrorResponseCode.ACCESS_DENIED.getRealCode())
        }

        var newPoint: Point? = null
        if (command.longitude != null && command.latitude != null) {
            val coordinate = Coordinate(command.longitude, command.latitude)
            newPoint = geometryFactory.createPoint(coordinate)
        }

        content.update(command.title, command.address, newPoint, command.memorizedAt)
        contentPersistenceAdapter.update(content)
    }

    @Transactional
    fun delete(contentId: Long) {
        // TODO: MethodArgumentResolver 이용해 컨트롤러단에서 바로 userId 생성 예정
        val userId = authService.getUserIdByAccessToken()
        val content = contentPersistenceAdapter.getById(contentId)
            ?: throw NoSuchElementException(ErrorResponseCode.NOT_FOUND_RESOURCE.getRealCode())
        if (content.userId != userId) {
            throw IllegalArgumentException(ErrorResponseCode.ACCESS_DENIED.getRealCode())
        }

        contentPersistenceAdapter.delete(content)
    }

}