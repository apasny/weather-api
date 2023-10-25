package com.yaroslav.apasov.weatherapi.repository

import com.yaroslav.apasov.weatherapi.entity.WeatherEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface WeatherRepository : JpaRepository<WeatherEntity, Long>, JpaSpecificationExecutor<WeatherEntity> {
}