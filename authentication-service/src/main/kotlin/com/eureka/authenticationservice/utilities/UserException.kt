package com.eureka.authenticationservice.utilities

open class UserException(
    override val message: String
) : RuntimeException(message)

class UserAlreadyRegistered: UserException(message = "User already registered. Please use different username.")