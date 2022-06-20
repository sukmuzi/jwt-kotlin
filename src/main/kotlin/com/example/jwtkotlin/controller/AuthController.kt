package com.example.jwtkotlin.controller

import com.example.jwtkotlin.dto.LoginDto
import com.example.jwtkotlin.dto.TokenDto
import com.example.jwtkotlin.jwt.JwtFilter
import com.example.jwtkotlin.jwt.TokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
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
    fun authorize(@Valid @RequestBody loginDto: LoginDto): ResponseEntity<TokenDto> {
        val authenticationToken: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password)

        val authentication: Authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        SecurityContextHolder.getContext().authentication = authentication

        val jwt = tokenProvider.createToken(authentication)
        println(jwt)
        val httpHeaders: HttpHeaders = HttpHeaders()
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer $jwt")

        return ResponseEntity(TokenDto(jwt), httpHeaders, HttpStatus.OK)
    }
}