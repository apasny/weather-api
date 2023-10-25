package com.yaroslav.apasov.weatherapi.controller

import com.yaroslav.apasov.weatherapi.dto.request.LoginDto
import com.yaroslav.apasov.weatherapi.dto.response.LoginResponseDto
import com.yaroslav.apasov.weatherapi.exception.ApiException
import com.yaroslav.apasov.weatherapi.service.UserService
import com.yaroslav.apasov.weatherapi.service.impl.TokenService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val LOGIN_URI: String = "/login"

@RestController
@RequestMapping(API_URI)
class AuthController(
    private val tokenService: TokenService,
    private val userService: UserService,
) {
    @PostMapping(LOGIN_URI)
    fun login(@RequestBody payload: LoginDto): LoginResponseDto {
        val user = userService.findUserByUsername(payload.email)

        if (payload.password != user.password) {
            throw ApiException(HttpStatus.BAD_REQUEST, "Login failed")
        }

        return LoginResponseDto(
            token = tokenService.createToken(user)
        )
    }
}