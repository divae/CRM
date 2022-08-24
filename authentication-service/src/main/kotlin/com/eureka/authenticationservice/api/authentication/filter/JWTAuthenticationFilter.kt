package com.eureka.authenticationservice.api.authentication.filter

import com.eureka.authenticationservice.api.authentication.config.AuthenticationConfigConstants
import com.eureka.authenticationservice.api.user.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthenticationFilter(
    authenticationManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        return try {
            val credentials = ObjectMapper().readValue(request.inputStream, User::class.java)
            authenticationManager!!.authenticate(
                UsernamePasswordAuthenticationToken(
                    credentials.username,
                    credentials.password,
                    ArrayList()
                )
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        chain: FilterChain?,
        auth: Authentication
    ) {
        val token = AuthenticationConfigConstants.bearerToken(
            (auth.principal as User).username,
            auth.authorities.iterator().next().authority
        )
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(
            "{\"$token\"}"
        )
    }
}
