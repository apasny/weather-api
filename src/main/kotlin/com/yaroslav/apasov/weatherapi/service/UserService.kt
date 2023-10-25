package com.yaroslav.apasov.weatherapi.service

import com.yaroslav.apasov.weatherapi.entity.UserEntity


interface UserService {
    fun findUserByUsername(username: String): UserEntity
    fun findById(userId: Long): UserEntity?
}