package kr.co.popin.infrastructure.config.geometry

import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GeometryConfig {

    @Bean
    fun geometryFactory(): GeometryFactory {
        return GeometryFactory(PrecisionModel(), 4326)
    }

}