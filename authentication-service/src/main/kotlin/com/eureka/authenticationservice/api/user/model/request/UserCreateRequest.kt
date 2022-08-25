package com.eureka.authenticationservice.api.user.model.request

import com.eureka.authenticationservice.api.user.model.Role
import com.eureka.authenticationservice.api.user.model.User

data class UserCreateRequest(
    val username: String,
    val password: String,
    val roles: List<Role>
) {
    fun toUser(): User = User(null, username, password, roles)
}