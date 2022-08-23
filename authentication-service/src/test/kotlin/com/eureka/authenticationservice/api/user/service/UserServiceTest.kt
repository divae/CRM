package com.eureka.authenticationservice.api.user.service

import com.eureka.authenticationservice.utilities.UserAlreadyRegistered
import com.eureka.authenticationservice.api.user.model.User
import com.eureka.authenticationservice.api.user.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

internal class UserServiceTest {
    private val userRepository: UserRepository = mockk()
    private val passwordEncoder: BCryptPasswordEncoder = mockk()
    private val userService = UserService(userRepository, passwordEncoder)

    @Test
    fun `Given user registered When readUserByUsername Then retrieve user`() {
        val username = "User1"
        val user = User(id = Long.MAX_VALUE, username = username, password = "pass", role = "ADMIN")
        every { userRepository.findByUsername(username) } returns user

        val userRegistered = userService.readUserByUsername(username)

        assertEquals(user, userRegistered)
    }

    @Test
    fun `Given user unregistered When readUserByUsername Then user not found`() {
        val username = "User1"
        every { userRepository.findByUsername(username) } returns null

        val userRegistered = userService.readUserByUsername(username)

        assertNull(userRegistered)
    }

    @Test
    fun `Given user registered When createUser Then UserException`() {
        val username = "User1"
        val user = User(id = Long.MAX_VALUE, username = username, password = "pass", role = "ADMIN")
        every { userRepository.findByUsername(username) } returns user

        val exception = assertThrows<UserAlreadyRegistered> { userService.createUser(user) }

        assertEquals("User already registered. Please use different username.", exception.message)
    }
}