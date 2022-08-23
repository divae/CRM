package com.eureka.authenticationservice.api.user.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val username: String,
    val password: String,
    val role: String
)