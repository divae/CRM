package com.eureka.authenticationservice.api.user.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RoleTest{
    @Test
    fun `Transform string to role`(){
        assertEquals(arrayListOf(Role.ADMIN,Role.USER), Role.transform("ADMIN, USER"))
    }
}