package com.eureka.authenticationservice.api.authentication.config

import com.eureka.authenticationservice.api.authentication.filter.JWTAuthentication
import java.util.*

class AuthenticationConfigConstants {
    companion object {
        const val SECRET = "eureka"
        const val EXPIRATION_TIME: Long = 864000000 // 10 days
        const val TOKEN_PREFIX = "Bearer "
        const val HEADER_STRING = "Authorization"
        fun bearerToken(username: String, role: String) = TOKEN_PREFIX + JWTAuthentication.token(
            username,
            Date(System.currentTimeMillis() + EXPIRATION_TIME),
            role,
            SECRET.toByteArray()
        )
    }
}