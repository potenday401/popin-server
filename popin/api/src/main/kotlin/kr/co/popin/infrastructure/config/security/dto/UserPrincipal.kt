package kr.co.popin.infrastructure.config.security.dto

import kr.co.popin.domain.model.user.persistence.dto.UserDto
import kr.co.popin.infrastructure.config.security.enums.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal (
    private val id: String,
    private val email: String,
    private val password: String,
    roles: Set<Role> = emptySet()
) : UserDetails {

    private val roles: Set<Role> = roles
        .ifEmpty {
            setOf(Role.USER)
        }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.map {
            SimpleGrantedAuthority(it.role)
        }.toMutableSet()
    }

    fun getUserId(): String {
        return id
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {
        fun newUserPrincipal(user: UserDto) =
            UserPrincipal(
                user.id,
                user.email,
                user.password
            )
    }
}