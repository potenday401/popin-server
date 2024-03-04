package kr.co.popin.infrastructure.config.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.*
import com.fasterxml.jackson.databind.DeserializationFeature.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.kotlinModule
import kr.co.popin.constants.TimeZoneConstants.KST
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME
import java.util.*

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val dateTimeFormatter = ISO_LOCAL_DATE_TIME

        return ObjectMapper().apply {
            enable(INDENT_OUTPUT)
            enable(WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)

            disable(WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
            disable(WRITE_DATES_AS_TIMESTAMPS)
            disable(WRITE_DURATIONS_AS_TIMESTAMPS)

            enable(FAIL_ON_READING_DUP_TREE_KEY)

            registerModules(
                kotlinModule {
                    enable(KotlinFeature.NullIsSameAsDefault)
                },
                JavaTimeModule().apply {
                    addSerializer(
                        LocalDateTime::class.java,
                        LocalDateTimeSerializer(dateTimeFormatter)
                    )
                    addDeserializer(
                        LocalDateTime::class.java,
                        LocalDateTimeDeserializer(dateTimeFormatter)
                    )
                    setTimeZone(TimeZone.getTimeZone(KST))
                    setLocale(Locale.KOREA)
                }
            )
        }
    }
}