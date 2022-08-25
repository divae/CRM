package com.eureka.authenticationservice.api.user.controller

import com.eureka.authenticationservice.api.user.model.Role
import com.eureka.authenticationservice.api.user.model.User
import com.eureka.authenticationservice.api.user.model.request.UserCreateRequest
import com.eureka.authenticationservice.api.user.model.response.UserCreateResponse
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
            id = 1,
            username = "User1",
            password = "000",
            roles = arrayListOf(Role.ADMIN)
        )

        val userRequest = UserCreateRequest(
            username = user.username,
            password = user.password,
            roles = user.roles
        )

        every { userService.createUser(any()) } returns user

        val response: MockHttpServletResponse = mvc.perform(
            post("/api/user")
                .content(jacksonObjectMapper().writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andReturn().response

        assertEquals(HttpStatus.CREATED.value(), response.status)
        assertEquals(
            jacksonObjectMapper().readValue(response.contentAsString, UserCreateResponse::class.java),
            user.toUserCreateResponse()
        )
    }

    //TODO when user is registered 409 conflict
}