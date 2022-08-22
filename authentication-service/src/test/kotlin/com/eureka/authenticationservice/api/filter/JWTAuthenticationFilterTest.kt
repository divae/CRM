package com.eureka.authenticationservice.api.filter

import com.eureka.authenticationservice.api.model.request.UserCreateRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException


internal class JWTAuthenticationFilterTest {

    private val authenticationManager: AuthenticationManager = mockk()
    private val jWTAuthenticationFilter = JWTAuthenticationFilter(authenticationManager)
    private val request = MockHttpServletRequest()
    private val response = MockHttpServletResponse()
    private val user = UserCreateRequest("User1", "pass")
    private val fakeUsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
        "principal",
        "credentials",
        listOf(SimpleGrantedAuthority("user"))
    )

    @Test
    fun `Given user authenticated When attemptAuthentication Then UsernamePasswordAuthenticationToken is generated`() {
        request.setContent(jacksonObjectMapper().writeValueAsString(user).toByteArray())
        mockkAuthenticationManagerAuthenticate returns fakeUsernamePasswordAuthenticationToken

        val authentication = jWTAuthenticationFilter.attemptAuthentication(request, response)

        Assertions.assertEquals(true, authentication.isAuthenticated)

    }


    @Test
    fun `Given an unregistered user when attempAuthentication Then not receive a token with UsernameNotFoundException`() {
        request.setContent(jacksonObjectMapper().writeValueAsString(user).toByteArray())
        mockkAuthenticationManagerAuthenticate throws UsernameNotFoundException("Error")

        Assertions.assertThrows(
            UsernameNotFoundException::class.java
        ) { jWTAuthenticationFilter.attemptAuthentication(request, response) }

    }

    @Test
    fun `Given void request When attemptAuthentication Then not receive a token with RuntimeException`() {
        mockkAuthenticationManagerAuthenticate returns fakeUsernamePasswordAuthenticationToken

        Assertions.assertThrows(
            RuntimeException::class.java
        ) { jWTAuthenticationFilter.attemptAuthentication(request, response) }

    }

    private val mockkAuthenticationManagerAuthenticate = every {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                user.username,
                user.password,
                ArrayList()
            )
        )
    }
}