package com.example.jwtkotlin.controller

import com.example.jwtkotlin.dto.UserDto
import com.example.jwtkotlin.entity.User
import com.example.jwtkotlin.jwt.JwtFilter
import com.example.jwtkotlin.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class UserController(@Autowired private val userService: UserService) {

    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody userDto: UserDto): ResponseEntity<User> {
        return ResponseEntity.ok(userService.signup(userDto))
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    fun getMyUserInfo(): ResponseEntity<User> {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities()?.get())
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun getUserInfo(@PathVariable username: String): ResponseEntity<User> {
        logger.debug("user role : ${userService.getUserWithAuthorities(username).get().authorities}")

        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get())
    }
}