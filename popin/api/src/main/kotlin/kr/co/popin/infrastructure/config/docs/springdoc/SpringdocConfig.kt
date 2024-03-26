package kr.co.popin.infrastructure.config.docs.springdoc

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment


@Profile("!prod")
@Configuration
class SpringdocConfig (
    private val environment: Environment
) {
    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .components(
            Components()
            .addSecuritySchemes("Authorization", authorization())
        )
        .addSecurityItem(
            SecurityRequirement()
                .addList("Authorization")
        )
        .info(apiInfo())

    @Bean
    fun configureGroupedOpenApi(
        groupedOpenApi: Map<String, GroupedOpenApi>,
        operationCustomizerWithEnums: OperationCustomizerWithEnums
    ): Map<String, GroupedOpenApi> {
        groupedOpenApi.forEach { (_: String, groupedOpenApi: GroupedOpenApi) ->
            groupedOpenApi.operationCustomizers
                .add(operationCustomizerWithEnums)
        }

        return groupedOpenApi
    }

    @Bean
    fun authorization(): SecurityScheme =
        SecurityScheme()
            .name("Authorization")
            .type(SecurityScheme.Type.HTTP)
            .`in`(SecurityScheme.In.HEADER)
            .scheme("Bearer")

    @Bean
    fun apiInfo(): Info = Info()
        .title("POPIN API DOCS")
        .version("1.0.0")
        .description(
            """
                Profile : ${environment.activeProfiles.last()}
            """.trimIndent()
        )

}