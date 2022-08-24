package com.eureka.authenticationservice.api.authentication.filter

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*


internal class JWTAuthenticationTest {
    @Test
    fun token() {
        val date = Date(1111111)
        val token =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJUb2tlbiIsInJvbGUiOiJhdXRoIiwiZXhwIjoxMTExfQ.jsLmwV9XHbYdMq8RFfEnE4oJJ51sBQd1IVDy1dj8kL42LCkkaxApcyCGS4j16zNDVsomBNXUt2LcwybBY3xTkQ"
        val secret = "Secret".toByteArray()
        val authenticator = "auth"

        Assertions.assertEquals(token, JWTAuthentication.token("Token", date, authenticator, secret))
    }
}

