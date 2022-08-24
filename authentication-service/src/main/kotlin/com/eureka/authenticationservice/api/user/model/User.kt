package com.eureka.authenticationservice.api.user.model

data class User(
    val id: Long?,
    val username: String,
    val password: String,
    val role: String
)