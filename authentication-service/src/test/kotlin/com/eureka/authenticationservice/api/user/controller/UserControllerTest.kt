package com.eureka.authenticationservice.api.user.controller

import com.eureka.authenticationservice.api.user.model.User
import com.eureka.authenticationservice.api.user.service.UserService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders


internal class UserControllerTest {
    lateinit var mvc: MockMvc

    @MockK
    lateinit var userService: UserService

    @InjectMockKs
    lateinit var userController: UserController

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        mvc = MockMvcBuilders.standaloneSetup(userController)
            .build()
    }


    @Test
    fun canRetrieveByNameWhenExists() {
        val user = User(
            id = null,
            username = "User1",
            password = "000",
            role = "ADMIN"
        )
        every { userService.createUser(any()) } returns user

        val response: MockHttpServletResponse = mvc.perform(
            post("/api/user")
                .content(jacksonObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andReturn().response
        
        assertEquals(response.status, HttpStatus.CREATED.value())
        assertEquals(user, jacksonObjectMapper().readValue(response.contentAsString, User::class.java))
    }
}