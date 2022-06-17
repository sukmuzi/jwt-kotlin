package com.example.jwtkotlin.controller

import com.example.jwtkotlin.dto.LoginDto
import com.example.jwtkotlin.jwt.TokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api")
class AuthController(
    @Autowired private val tokenProvider: TokenProvider,
    @Autowired private val authenticationManagerBuilder: AuthenticationManagerBuilder
) {

    @PostMapping("/authenticate")
    fun authorize(@Valid @RequestBody loginDto: LoginDto) {
        
    }
}