package com.yaroslav.apasov.weatherapi.repository

import com.yaroslav.apasov.weatherapi.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): Optional<UserEntity>
}