package kr.co.popin.application.content

import kr.co.popin.domain.model.content.Content
import kr.co.popin.infrastructure.persistence.content.ContentPersistenceAdapter
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ContentService(
        private val contentPersistenceAdapter: ContentPersistenceAdapter,
        private val geometryFactory: GeometryFactory
) {

    // TODO: Photo 도메인 설계 후 DTO로 변경 고려
    fun post(title: String, address: String, latitude: Double, longitude: Double, createdDateTime: LocalDateTime): Content {
        val coordinate = Coordinate(longitude, latitude)
        val point = geometryFactory.createPoint(coordinate)

        val content = Content.create(title, address, point, createdDateTime)
        return contentPersistenceAdapter.save(content)
    }

}