package kr.co.popin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PopinApiApplication

fun main(args: Array<String>) {
    runApplication<PopinApiApplication>(*args)
}