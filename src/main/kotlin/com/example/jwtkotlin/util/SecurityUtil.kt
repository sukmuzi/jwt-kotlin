package com.example.jwtkotlin.util

import com.example.jwtkotlin.jwt.JwtFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class SecurityUtil {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SecurityUtil::class.java)

        fun getCurrentUsername(): Optional<String> {
            val authentication: Authentication = SecurityContextHolder.getContext().authentication

            if (authentication == null) {
                logger.debug("Security Context 에 인증 정보가 없습니다.")

                return Optional.empty<String>()
            }

            var username: String? = null
            if (authentication.principal is UserDetails) {
                var springSecurityUser: UserDetails = authentication.principal as UserDetails
                username = springSecurityUser.username
            } else if (authentication.principal is String) {
                username = authentication.principal as String
            }

            return Optional.ofNullable(username)
        }
    }
}