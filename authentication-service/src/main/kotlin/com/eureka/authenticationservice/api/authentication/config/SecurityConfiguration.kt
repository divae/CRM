package com.eureka.authenticationservice.api.authentication.config

import com.eureka.authenticationservice.api.authentication.filter.JWTAuthenticationFilter
import com.eureka.authenticationservice.api.authentication.filter.JWTAuthorizationFilter
import com.eureka.authenticationservice.api.authentication.service.AuthenticationUserDetailService
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@EnableWebSecurity
class SecurityConfiguration(
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val authenticationUserDetailService: AuthenticationUserDetailService
) : WebSecurityConfigurerAdapter() { // TODO look this https://codejava.net/frameworks/spring-boot/fix-websecurityconfigureradapter-deprecated#:~:text=The%20type%20WebSecurityConfigurerAdapter%20is%20deprecated%20So%2C%20why%20Spring,users%20to%20move%20towards%20a%20component-based%20security%20configuration.

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable().authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/user").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(JWTAuthenticationFilter(authenticationManager()))
            .addFilter(JWTAuthorizationFilter(authenticationManager())) // this disables session creation on Spring Security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder)
    }
}