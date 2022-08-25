package com.eureka.authenticationservice.api.authentication.service

import com.eureka.authenticationservice.api.user.model.Role
import com.eureka.authenticationservice.api.user.model.User
import com.eureka.authenticationservice.api.user.service.UserService
import com.eureka.authenticationservice.utilities.UserUnregisteredException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AuthenticationUserDetailServiceTest{
    @MockK
    val userService: UserService = mockk()

    @InjectMockKs
    private val authenticationService: AuthenticationUserDetailService = AuthenticationUserDetailService(userService)
    @Test
    fun  `Given user registered when loadUserByUsername`(){
        val username = "User1"
        val user = User(id = Long.MAX_VALUE, username = username, password = "pass", roles = arrayListOf(Role.ADMIN))
        every { userService.readUserByUsername(username) } returns user

        assertEquals(user.username, authenticationService.loadUserByUsername(username).username)
    }

    @Test
    fun `Given user unregistered when loadUserByUsername Then UsernameNotFoundException`(){
        val username = "User1"
        every { userService.readUserByUsername(username) } returns null

        val exception = assertThrows<UserUnregisteredException> {
            authenticationService.loadUserByUsername(username).username
        }

        assertEquals("The user $username is unregistered. Please check your username", exception.message)
    }
}