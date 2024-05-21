package kr.co.popin.base

import kr.co.popin.infrastructure.config.security.dto.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationBaseController {
    fun getCurrentLoggedUserPrincipal(): UserPrincipal? {
        return SecurityContextHolder
            .getContext()
            ?.authentication
            ?.principal as UserPrincipal?
    }
}