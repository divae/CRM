package com.eureka.authenticationservice.api.authentication.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JWTAuthentication {
    companion object{
        fun token(username: String, date: Date, role: String, secret: ByteArray): String = JWT.create()
            .withSubject(username)
            .withClaim("role", role)
            .withExpiresAt(date)
            .sign(Algorithm.HMAC512(secret))
    }

}