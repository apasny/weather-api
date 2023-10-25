package com.yaroslav.apasov.weatherapi.config

import com.yaroslav.apasov.weatherapi.dto.response.MessageResponseDto
import com.yaroslav.apasov.weatherapi.exception.ApiException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ApiException::class)
    fun handleBusinessException(ex: ApiException): ResponseEntity<MessageResponseDto?>? {
        return ResponseEntity.status(ex.httpStatus).body<MessageResponseDto>(MessageResponseDto(ex.message))
    }
}