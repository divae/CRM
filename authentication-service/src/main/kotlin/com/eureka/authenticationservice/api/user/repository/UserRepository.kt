package com.eureka.authenticationservice.api.user.repository
import com.eureka.authenticationservice.api.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}