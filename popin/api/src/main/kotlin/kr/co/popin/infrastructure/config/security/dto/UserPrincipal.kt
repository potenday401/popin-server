package kr.co.popin.infrastructure.config.security.dto

import kr.co.popin.domain.model.user.vo.UserEmail
import kr.co.popin.domain.model.user.vo.UserId
import kr.co.popin.domain.model.user.vo.UserPassword
import kr.co.popin.infrastructure.config.security.enums.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal (
    id: UserId,
    email: UserEmail,
    password: UserPassword,
    roles: Set<Role> = emptySet()
) : UserDetails {

    private val id: String = id.id
    private val email: String = email.email
    private val password: String = password.password
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
}