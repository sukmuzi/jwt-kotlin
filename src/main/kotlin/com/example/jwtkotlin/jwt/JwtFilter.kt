package com.example.jwtkotlin.jwt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtFilter(@Autowired private var tokenProvider: TokenProvider) : GenericFilterBean() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JwtFilter::class.java)
        const val AUTHORIZATION_HEADER: String = "Authorization"
    }

    //private var tokenProvider: TokenProvider = tokenProvider

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        var httpServletRequest: HttpServletRequest = request as HttpServletRequest
        var jwt: String? = resolveToken(httpServletRequest)
        var requestURI: String = httpServletRequest.requestURI

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            var authentication: Authentication = tokenProvider.getAuthentication(jwt)
            SecurityContextHolder.getContext().authentication = authentication
            logger.debug("Security Context 에 ${authentication.name} 인증 정보 저장. uri: $requestURI")
        } else {
            logger.debug("유효한 JWT 토큰 없음. uri: $requestURI")
        }

        chain?.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        var bearerToken: String = request.getHeader(AUTHORIZATION_HEADER)

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }

        return null
    }
}