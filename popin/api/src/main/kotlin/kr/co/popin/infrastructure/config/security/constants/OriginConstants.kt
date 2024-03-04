package kr.co.popin.infrastructure.config.security.constants

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
class OriginConstantsProvider (
    private val originConstants: List<IOriginConstants>
) {
    fun getAll(): List<String> = originConstants
        .flatMap { it.getAll() }
}

interface IOriginConstants {
    fun getAll(): List<String>
}

abstract class AbstractOriginConstants : IOriginConstants {
    override fun getAll(): List<String> = listOf("*")
}

@Profile("local")
@Component
object LocalOriginConstants : AbstractOriginConstants()

@Profile("dev")
@Component
object DevOriginConstants : AbstractOriginConstants()

@Profile("prod")
@Component
object ProdOriginConstants : AbstractOriginConstants()
