package kr.co.popin.infrastructure.config.security.authorization

import org.springframework.http.HttpMethod
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.stereotype.Component

@Component
class RequestMatchers {
    val permitAll: List<RequestMatcher> = listOf(
        AntPathRequestMatcher("/error"),
        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/users/duplicate-check"),
        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/users/sign-up"),
        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/users/login")
    )
}