package com.eureka.authenticationservice.api.user.repository

import com.eureka.authenticationservice.api.user.model.Role
import com.eureka.authenticationservice.api.user.model.User
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
@Table(name = "user")
class UserDto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val username: String,
    val password: String,
    val roles: ArrayList<Role>
) {
    fun toUser() = User(id, username, password, roles)
}
