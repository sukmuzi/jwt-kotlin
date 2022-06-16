package com.example.jwtkotlin.service

import com.example.jwtkotlin.entity.User
import com.example.jwtkotlin.jwt.TokenProvider
import com.example.jwtkotlin.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collectors
import kotlin.streams.toList

class CustomUserDetailsService(userRepository: UserRepository) : UserDetailsService {

    private val userRepository: UserRepository = userRepository

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findOneWithAuthoritiesByUsername(username)
            .map { user -> createUser(username, user) }
            .orElseThrow {
                () -> UsernameNotFoundException("$username 을 찾을 수 없습니다.")
            }
    }

    fun createUser(username: String, user: User?): org.springframework.security.core.userdetails.User? {
        return user?.let {
            if (user.activated == false) {
                throw RuntimeException("$username -> 활성화되어 있지 않습니다.")
            }

            var grantedAuthorities: List<GrantedAuthority>? = user.authorities?.stream()
                ?.map { authority -> SimpleGrantedAuthority(authority.authorityName) }
                ?.collect(Collectors.toList())

            return org.springframework.security.core.userdetails.User(user.username, user.password, grantedAuthorities)
        }?: kotlin.run {
            null
        }
    }
}