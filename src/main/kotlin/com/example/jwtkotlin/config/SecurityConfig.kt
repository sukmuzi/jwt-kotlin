package com.example.jwtkotlin.config

import com.example.jwtkotlin.jwt.*
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    tokenProvider: TokenProvider,
    jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    jwtAccessDeniedHandler: JwtAccessDeniedHandler
) : WebSecurityConfigurerAdapter() {

    private val tokenProvider: TokenProvider = tokenProvider
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler = jwtAccessDeniedHandler

    override fun configure(http: HttpSecurity?) {
        http?.csrf()?.disable()
            ?.exceptionHandling()
            ?.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            ?.accessDeniedHandler(jwtAccessDeniedHandler)

            ?.and()
            ?.headers()
            ?.frameOptions()
            ?.sameOrigin()

            ?.and()
            ?.sessionManagement()
            ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            ?.and()
            ?.authorizeRequests()
            ?.antMatchers("/api/hello")?.permitAll()
            ?.antMatchers("/api/authenticate")?.permitAll()
            ?.antMatchers("/api/signup")?.permitAll()
            ?.anyRequest()?.authenticated()

            ?.and()
            ?.apply(JwtSecurityConfig(tokenProvider))
    }
}