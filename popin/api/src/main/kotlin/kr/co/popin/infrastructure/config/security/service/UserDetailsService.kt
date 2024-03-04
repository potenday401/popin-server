package kr.co.popin.infrastructure.config.security.service

import kr.co.popin.domain.model.user.persistence.IUserRepository
import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.infrastructure.config.security.dto.UserPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsService (
    private val userRepository: IUserRepository
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val userEmail = UserEmail.newUserEmail(username)
        val user = userRepository.findByEmail(userEmail)
            ?: throw UsernameNotFoundException("user not found.")

        return UserPrincipal.newUserPrincipal(user)
    }
}