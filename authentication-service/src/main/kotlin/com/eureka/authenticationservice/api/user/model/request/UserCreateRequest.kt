package com.eureka.authenticationservice.api.user.model.request

import com.eureka.authenticationservice.api.user.model.User

data class UserCreateRequest(
    val username: String,
    val password: String,
    val role: String
) {
    fun toUser(): User = User(null, username, password, role)
}