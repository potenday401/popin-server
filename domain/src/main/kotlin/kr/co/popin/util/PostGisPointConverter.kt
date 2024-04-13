package kr.co.popin.util

import org.jooq.Converter
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel

class PostGisPointConverter : Converter<Any, Point> {

    private val geometryFactory = GeometryFactory(PrecisionModel(), 4326)

    override fun from(databaseObject: Any?): Point? {
        if (databaseObject is net.postgis.jdbc.geometry.Point) {
            val coordinate = Coordinate(databaseObject.y, databaseObject.x)
            return geometryFactory.createPoint(coordinate)
        }
        return null
    }

    override fun to(point: Point?): Any? {
        if (point == null) {
            return null
        }
        val pgPoint = net.postgis.jdbc.geometry.Point().apply {
            srid = point.srid
            x = point.x
            y = point.y
        }
        pgPoint.haveMeasure = false
        return pgPoint
    }

    override fun toType(): Class<Point> {
        return Point::class.java
    }

    override fun fromType(): Class<Any> {
        return Any::class.java
    }

}
