package com.eureka.authenticationservice.api.authentication.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.eureka.authenticationservice.api.authentication.config.AuthenticationConfigConstants
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(authenticationManager: AuthenticationManager?) :
    BasicAuthenticationFilter(authenticationManager) {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(AuthenticationConfigConstants.HEADER_STRING)
        if (header == null || !header.startsWith(AuthenticationConfigConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }
        val authentication = getAuthentication(request)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest?): UsernamePasswordAuthenticationToken? {
        val token: kotlin.String? = request?.getHeader(AuthenticationConfigConstants.HEADER_STRING)
        if (token != null) {
            // parse the token.
            val verify: DecodedJWT? =
                JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.toByteArray()))
                    .build()
                    .verify(token.replace(AuthenticationConfigConstants.TOKEN_PREFIX, ""))
            val username: kotlin.String? = verify?.subject
            val role: kotlin.String? = verify?.getClaim("role")?.asString()
            if (username != null) {
                return UsernamePasswordAuthenticationToken(username, null, role?.let { getAuthorities(it) })
            }
            return null
        }
        return null
    }

    private fun getAuthorities(role: String): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role))
    }

}