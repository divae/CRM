package com.eureka.authenticationservice.api.user.controller

import com.eureka.authenticationservice.api.user.model.request.UserCreateRequest
import com.eureka.authenticationservice.api.user.model.response.UserCreateResponse
import com.eureka.authenticationservice.api.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = ["/api/user"])
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createUser(@RequestBody userCreateRequest: UserCreateRequest): ResponseEntity<*> {
        val user = userService.createUser(userCreateRequest.toUser())
        //TODO when user is registered 409 conflict
        return ResponseEntity<UserCreateResponse>(user.toUserCreateResponse(), HttpStatus.CREATED)
    }
}