package kr.co.popin.application.content

import kr.co.popin.application.auth.AuthService
import kr.co.popin.application.content.dtos.PostContentCommand
import kr.co.popin.domain.model.content.Content
import kr.co.popin.infrastructure.persistence.content.ContentPersistenceAdapter
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContentService(
    private val authService: AuthService,
    private val contentPersistenceAdapter: ContentPersistenceAdapter,
    private val geometryFactory: GeometryFactory
) {

    @Transactional
    fun post(contentCommand: PostContentCommand): Content {
        val userId = authService.getUserIdByAccessToken()
        val coordinate = Coordinate(contentCommand.longitude, contentCommand.latitude)
        val point = geometryFactory.createPoint(coordinate)
        return contentPersistenceAdapter.save(userId = userId,
                                              title = contentCommand.title,
                                              address = contentCommand.address,
                                              point = point)
    }

}