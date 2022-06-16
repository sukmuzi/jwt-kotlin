package com.example.jwtkotlin.service

import com.example.jwtkotlin.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(userRepository: UserRepository, passwordEncoder: PasswordEncoder) {

    private val userRepository: UserRepository = userRepository
    private val passwordEncoder: PasswordEncoder = passwordEncoder
}