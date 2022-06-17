package com.example.jwtkotlin.controller

import com.example.jwtkotlin.dto.UserDto
import com.example.jwtkotlin.entity.User
import com.example.jwtkotlin.jwt.JwtFilter
import com.example.jwtkotlin.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class UserController(userService: UserService) {

    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    private val userService: UserService = userService

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody userDto: UserDto): ResponseEntity<User> {
        return ResponseEntity.ok(userService.signup(userDto))
    }
}