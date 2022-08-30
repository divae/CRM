package com.eureka.authenticationservice.api.user.model

import com.eureka.authenticationservice.api.user.model.response.UserCreateResponse
import com.eureka.authenticationservice.api.user.repository.UserDto

data class User(
    val id: Long?,
    val username: String,
    val password: String,
    val roles: List<Role>
) {
    fun toUserDto() = UserDto(id, username, password, ArrayList(roles))
    fun toUserCreateResponse() = UserCreateResponse(id!!, username)
}