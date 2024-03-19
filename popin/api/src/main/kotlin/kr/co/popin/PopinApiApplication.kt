package kr.co.popin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
class PopinApiApplication

fun main(args: Array<String>) {
    runApplication<PopinApiApplication>(*args)
}