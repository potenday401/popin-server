package kr.co.popin.infrastructure.config.security.cors

import kr.co.popin.infrastructure.config.security.constants.OriginConstantsProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig (
    private val originConstantsProvider: OriginConstantsProvider
) {
    @Bean
    fun corsConfiguration(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = originConstantsProvider.getAll()
            allowedHeaders = listOf("*")
            allowedMethods = listOf(HttpMethod.POST.name(), HttpMethod.GET.name(), HttpMethod.PUT.name(), HttpMethod.PATCH.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name())
            allowCredentials = true
            maxAge = 3600
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}