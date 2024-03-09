package kr.co.popin.infrastructure.config.security.service

import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.infrastructure.config.security.dto.UserPrincipal
import kr.co.popin.infrastructure.persistence.user.UserPersistenceAdapter
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsService (
    private val userPersistenceAdapter: UserPersistenceAdapter
) : UserDetailsService {
    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userPersistenceAdapter.findByEmail(UserEmail(username))
            ?: throw UsernameNotFoundException("user not found.")

        return UserPrincipal(
            user.id,
            user.email,
            user.password
        )
    }
}