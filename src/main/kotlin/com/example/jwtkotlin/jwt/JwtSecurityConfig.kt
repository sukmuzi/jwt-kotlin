package com.example.jwtkotlin.jwt

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain

class JwtSecurityConfig(tokenProvider: TokenProvider) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    private var tokenProvider: TokenProvider = tokenProvider

    override fun configure(builder: HttpSecurity?) {

    }
}