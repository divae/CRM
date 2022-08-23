package com.eureka.authenticationservice.api.authentication.service

import com.eureka.authenticationservice.api.user.service.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationUserDetailService(
    private val userService: UserService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails = userService.readUserByUsername(username)?.let {
        User(
            it.username,
            it.password,
            getAuthorities(it.role)
        )
    } ?: throw UsernameNotFoundException(username)


    private fun getAuthorities(role: String): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role))
    }
}