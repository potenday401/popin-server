package kr.co.popin.infrastructure.persistence.content.query

import org.locationtech.jts.geom.Polygon

data class ContentQueryCondition(
    val userId: String,
    val area: Polygon,
)
