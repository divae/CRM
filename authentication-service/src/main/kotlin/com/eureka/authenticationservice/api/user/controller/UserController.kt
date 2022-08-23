package com.eureka.authenticationservice.api.user.controller

import com.eureka.authenticationservice.api.user.model.request.UserCreateRequest
import com.eureka.authenticationservice.api.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/user"])
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createUser(@RequestBody userCreateRequest: UserCreateRequest): ResponseEntity<*> {
        userService.createUser(userCreateRequest.toUser())
        return ResponseEntity.ok().build<Any>()
    }
}