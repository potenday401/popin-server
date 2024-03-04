package kr.co.popin.infrastructure.config.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class JacksonConfig {

    @Bean
    fun mappingJackson2HttpMessageConverter(
        objectMapper: ObjectMapper
    ): MappingJackson2HttpMessageConverter =
        MappingJackson2HttpMessageConverter(objectMapper)
}