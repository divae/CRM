package com.eureka.authenticationservice.api.config

class AuthenticationConfigConstants {
    companion object{
        val SECRET = "eureka"
        val EXPIRATION_TIME: Long = 864000000 // 10 days

        val TOKEN_PREFIX = "Bearer "
        val HEADER_STRING = "Authorization"
        val SIGN_UP_URL = "/api/user"
    }
}