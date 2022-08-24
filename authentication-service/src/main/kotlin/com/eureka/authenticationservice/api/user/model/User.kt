package com.eureka.authenticationservice.api.user.model

import com.eureka.authenticationservice.api.user.model.response.UserCreateResponse
import com.eureka.authenticationservice.api.user.repository.UserDto

data class User(
    val id: Long?,
    val username: String,
    val password: String,
    val role: String
) {
    fun toUserDto() = UserDto(id, username, password, role)
    fun toUserCreateResponse() = UserCreateResponse(id!!, username)
}