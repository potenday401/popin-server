package kr.co.popin.infrastructure.config.security.authorization

import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.stereotype.Component

@Component
class RequestMatchers (
    environment: Environment
) {
    private val activeProfiles = setOf(*environment.activeProfiles)

    val permitAll: List<RequestMatcher> = mutableListOf(
        AntPathRequestMatcher("/error"),
        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/users/duplicate-check"),
        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/users/sign-up"),
        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/users/login"),
        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/users/send/email/confirm-code"),
        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/users/verify/email/confirm-code"),
    ).apply {
        if ("prod" !in activeProfiles) {
            add(AntPathRequestMatcher("/docs/**"))
        }
    }.toList()
}