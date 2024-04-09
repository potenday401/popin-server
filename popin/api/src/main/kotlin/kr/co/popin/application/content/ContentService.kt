package kr.co.popin.application.content

import kr.co.popin.application.auth.AuthService
import kr.co.popin.application.content.dtos.PostContentCommand
import kr.co.popin.domain.model.content.Content
import kr.co.popin.infrastructure.persistence.content.ContentPersistenceAdapter
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.stereotype.Service

@Service
class ContentService(
    private val authService: AuthService,
    private val contentPersistenceAdapter: ContentPersistenceAdapter,
    private val geometryFactory: GeometryFactory
) {

    fun post(contentCommand: PostContentCommand): Content {
        val userId = authService.getUserIdByAccessToken()
        val coordinate = Coordinate(contentCommand.longitude, contentCommand.latitude)
        val point = geometryFactory.createPoint(coordinate)
        return contentPersistenceAdapter.save(userId,
                                              contentCommand.title,
                                              contentCommand.address,
                                              point)
    }

}