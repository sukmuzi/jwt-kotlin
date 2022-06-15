package com.example.jwtkotlin.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component

import java.security.Key
import java.security.SignatureException
import java.util.Arrays
import java.util.Date
import java.util.stream.Collectors

@Component
class TokenProvider(
    @Value("\${jwt.secret}") var secretKey: String,
    @Value("\${jwt.token-validity-in-seconds}") var tokenValidityInSeconds: Long
    ) : InitializingBean {

    private var tokenValidityInMilliseconds: Long = 0
    private lateinit var secret: String
    private lateinit var key: Key

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TokenProvider::class.java)
        private const val AUTHORITIES_KEY: String = "auth"
    }

    override fun afterPropertiesSet() {
        this.secret = secretKey
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000
        val keyBytes: ByteArray = Decoders.BASE64.decode(secret)
        this.key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun createToken(authentication: Authentication): String? {
        val authorities: String = authentication.authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","))

        var now: Long = Date().time
        var validity: Date = Date(now + this.tokenValidityInMilliseconds)

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun getAuthentication(token: String?): Authentication {
        var claims: Claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        var authorities: Collection<out GrantedAuthority> =
            Arrays.stream(claims[AUTHORITIES_KEY].toString().split(",").toTypedArray())
                .map(::SimpleGrantedAuthority)
                .collect(Collectors.toList())

        var principal: User = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)

            return true
        } catch (e: SignatureException) {
            logger.error("잘못된 JWT 서명 : $e")
        } catch (e: ExpiredJwtException) {
            logger.error("만료된 JWT 토큰 $e")
        } catch (e: UnsupportedJwtException) {
            logger.error("지원되지 않는 JWT 토큰 $e")
        } catch (e: IllegalArgumentException) {
            logger.error("잘못된 JWT 토큰 $e")
        }

        return false
    }
}