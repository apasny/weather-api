package com.yaroslav.apasov.weatherapi.service.impl

import com.yaroslav.apasov.weatherapi.entity.UserEntity
import com.yaroslav.apasov.weatherapi.exception.ApiException
import com.yaroslav.apasov.weatherapi.exception.USER_EMAIL_NOT_FOUND
import com.yaroslav.apasov.weatherapi.exception.USER_ID_NOT_FOUND
import com.yaroslav.apasov.weatherapi.repository.UserRepository
import com.yaroslav.apasov.weatherapi.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepository: UserRepository) : UserService {
    override fun findUserByUsername(username: String): UserEntity {
        return userRepository.findByEmail(username)
            .orElseThrow { ApiException(HttpStatus.NOT_FOUND, USER_EMAIL_NOT_FOUND) }
    }

    override fun findById(userId: Long): UserEntity? {
        return userRepository.findById(userId)
            .orElseThrow { ApiException(HttpStatus.NOT_FOUND, USER_ID_NOT_FOUND) }
    }
}