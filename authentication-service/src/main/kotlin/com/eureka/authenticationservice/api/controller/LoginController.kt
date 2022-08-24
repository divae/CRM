package com.eureka.authenticationservice.api.controller

import com.eureka.authenticationservice.api.authentication.config.AuthenticationConfigConstants
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/login"])
class LoginController {

    @PostMapping
    fun login(@RequestBody userLoginRequest: UserLoginRequest): ResponseEntity<*> {
        val token = AuthenticationConfigConstants.token(userLoginRequest.username, "ADMIN")
        return ResponseEntity(UserLoginResponse(token), HttpStatus.CREATED)
    }
}

class UserLoginRequest(val username: String, val password: String)
class UserLoginResponse(val token: String)