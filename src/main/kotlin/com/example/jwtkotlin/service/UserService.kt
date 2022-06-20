package com.example.jwtkotlin.service

import com.example.jwtkotlin.dto.UserDto
import com.example.jwtkotlin.entity.Authority
import com.example.jwtkotlin.entity.User
import com.example.jwtkotlin.repository.UserRepository
import com.example.jwtkotlin.util.SecurityUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(userRepository: UserRepository, passwordEncoder: PasswordEncoder) {

    private val userRepository: UserRepository = userRepository

    private val passwordEncoder: PasswordEncoder = passwordEncoder

    @Transactional
    fun signup(userDto: UserDto) : User {

        if (userRepository.findOneWithAuthoritiesByUsername(userDto.username).orElse(null) != null) {
            throw RuntimeException("이미 가입되어있는 유저입니다.")
        }

        var authority: Authority = Authority("ROLE_USER")

        var user: User = User().apply {
            this.username = userDto.username
            this.password = passwordEncoder.encode(userDto.password)
            this.nickname = userDto.nickname
            this.authorities = Collections.singleton(authority)
            this.activated = true
        }

        return userRepository.save(user)
    }

    @Transactional
    fun getMyUserWithAuthorities(): Optional<User>? {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername)
    }

    @Transactional
    fun getUserWithAuthorities(username: String): Optional<User> {
        return userRepository.findOneWithAuthoritiesByUsername(username)
    }
}