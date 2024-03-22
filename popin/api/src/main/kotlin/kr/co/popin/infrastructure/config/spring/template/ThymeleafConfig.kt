package kr.co.popin.infrastructure.config.spring.template

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver

@Configuration
class ThymeleafConfig {
    @Bean
    fun templateEngine(
        springResourceTemplateResolver: SpringResourceTemplateResolver
    ) = SpringTemplateEngine()
        .apply {
            addTemplateResolver(springResourceTemplateResolver)
        }
}