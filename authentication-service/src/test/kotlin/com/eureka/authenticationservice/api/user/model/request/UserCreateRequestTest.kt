package com.eureka.authenticationservice.api.user.model.request

import com.eureka.authenticationservice.api.user.model.Role
import com.eureka.authenticationservice.api.user.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserCreateRequestTest{
    @Test
    fun `Transform to user`(){
        val userCreate = UserCreateRequest(
            "User",
            "11",
            listOf(Role.ADMIN,Role.USER)
        )
        val user = userCreate.toUser()

        assertEquals(User(null,"User","11", arrayListOf(Role.ADMIN,Role.USER)), user)
    }

}
