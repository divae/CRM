package com.eureka.authenticationservice.utilities

open class UserException(
    override val message: String
) : RuntimeException(message)

class UserAlreadyRegistered : UserException(message = "User already registered. Please use different username.")
class UserUnregisteredException(username:String) : UserException(message = "The user $username is unregistered. Please check your username")