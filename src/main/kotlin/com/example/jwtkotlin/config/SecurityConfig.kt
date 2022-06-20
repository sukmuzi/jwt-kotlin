package com.example.jwtkotlin.config

import com.example.jwtkotlin.jwt.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Autowired private val tokenProvider: TokenProvider,
    @Autowired private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    @Autowired private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        web?.ignoring()
            ?.antMatchers("/h2-console/**",
            "/favicon.ico")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity?) {
        http?.authorizeRequests()
            ?.antMatchers("/api/hello")?.permitAll()
            ?.antMatchers("/api/authenticate")?.permitAll()
            ?.antMatchers("/api/signup")?.permitAll()
            ?.anyRequest()?.authenticated()

            ?.and()
            ?.csrf()
            ?.disable()
            ?.exceptionHandling()
            ?.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            ?.accessDeniedHandler(jwtAccessDeniedHandler)

            ?.and()
            ?.apply(JwtSecurityConfig(tokenProvider))


//        http?.csrf()?.disable()
//            ?.exceptionHandling()
//            ?.authenticationEntryPoint(jwtAuthenticationEntryPoint)
//            ?.accessDeniedHandler(jwtAccessDeniedHandler)
//
//            ?.and()
//            ?.headers()
//            ?.frameOptions()
//            ?.sameOrigin()
//
//            ?.and()
//            ?.sessionManagement()
//            ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//            ?.and()
//            ?.authorizeRequests()
//            ?.antMatchers("/api/hello")?.permitAll()
//            ?.antMatchers("/api/authenticate")?.permitAll()
//            ?.antMatchers("/api/signup")?.permitAll()
//            ?.anyRequest()?.authenticated()
//
//            ?.and()
//            ?.apply(JwtSecurityConfig(tokenProvider))
    }
}