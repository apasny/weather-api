package com.yaroslav.apasov.weatherapi.config

import com.yaroslav.apasov.weatherapi.service.impl.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.web.SecurityFilterChain

const val PUBLIC_ENDPOINT = "/api/v1/**"
const val SECURE_ENDPOINT = "/api/v1/weather"
const val LOGIN_ENDPOINT = "/api/v1/login"

@Configuration
@EnableWebSecurity
class SecurityConfig(private val tokenService: TokenService) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINT).permitAll()
                    .requestMatchers(HttpMethod.POST, LOGIN_ENDPOINT).permitAll()
                    .requestMatchers(HttpMethod.POST, SECURE_ENDPOINT).hasAuthority("EDITOR")
                    .anyRequest().permitAll()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2
                    .jwt(Customizer.withDefaults())
            }
            .authenticationManager { auth ->
                val jwt = auth as BearerTokenAuthenticationToken
                val user = tokenService.parseToken(jwt.token) ?: throw InvalidBearerTokenException("Invalid token")
                UsernamePasswordAuthenticationToken(user.email, user.password, listOf(SimpleGrantedAuthority(user.role.uppercase())))
            }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.disable() }
            .httpBasic {}
        return http.build()
    }

}