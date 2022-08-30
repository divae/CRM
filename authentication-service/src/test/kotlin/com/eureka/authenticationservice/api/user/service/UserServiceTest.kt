package com.eureka.authenticationservice.api.user.service

import com.eureka.authenticationservice.api.user.model.Role
import com.eureka.authenticationservice.api.user.model.User
import com.eureka.authenticationservice.api.user.repository.UserRepository
import com.eureka.authenticationservice.utilities.UserAlreadyRegistered
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

internal class UserServiceTest {
    private val userRepository: UserRepository = mockk()
    private val passwordEncoder: BCryptPasswordEncoder = mockk()
    @InjectMockKs
    private val userService = UserService(userRepository, passwordEncoder)

    @Test
    fun `Given user registered When readUserByUsername Then retrieve user`() {
        val username = "User1"
        val user = User(id = Long.MAX_VALUE, username = username, password = "pass", roles = arrayListOf(Role.USER, Role.ADMIN))
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
        val user = User(id = Long.MAX_VALUE, username = username, password = "pass", roles = arrayListOf(Role.ADMIN))
        every { userRepository.findByUsername(username) } returns user

        val exception = assertThrows<UserAlreadyRegistered> { userService.createUser(user) }

        assertEquals("User already registered. Please use different username.", exception.message)
    }

    @Test
    fun `Given user unregistered When createUser Then retrieve it`() {
        val username = "User1"
        val user = User(id = null, username = username, password = "123456", roles = arrayListOf(Role.ADMIN))
        every { userRepository.findByUsername(username) } returns null
        every { passwordEncoder.encode(user.password)} returns "123456"
        every { userRepository.save(any())} returns user.toUserDto()

        assertEquals(user, userService.createUser(user))
    }
}