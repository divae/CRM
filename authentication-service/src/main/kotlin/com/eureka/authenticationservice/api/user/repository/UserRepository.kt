package com.eureka.authenticationservice.api.user.repository

import com.eureka.authenticationservice.api.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserDto, Long> {
    fun findByUsername(username: String): User?
    
    fun save(userDto: UserDto): UserDto
}