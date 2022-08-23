package com.eureka.authenticationservice.api.user.service


import com.eureka.authenticationservice.utilities.UserAlreadyRegistered
import com.eureka.authenticationservice.api.user.model.User
import com.eureka.authenticationservice.api.user.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun readUserByUsername(username: String): User? = userRepository.findByUsername(username)

    fun createUser(user: User) {
        val byUsername: User? = userRepository.findByUsername(user.username)
        if (byUsername != null) {
            throw UserAlreadyRegistered()
        }
        val encoderUser = User(
            null,
            user.username,
            passwordEncoder.encode(user.password),
            user.role
        )
        userRepository.save(encoderUser)
    }
}