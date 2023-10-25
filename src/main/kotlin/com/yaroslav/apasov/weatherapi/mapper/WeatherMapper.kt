package com.yaroslav.apasov.weatherapi.mapper

import com.yaroslav.apasov.weatherapi.dto.response.WeatherResponseDto
import com.yaroslav.apasov.weatherapi.entity.WeatherEntity
import org.springframework.stereotype.Service

@Service
class WeatherMapper {

    fun toWeatherResponseDto(weatherEntity: WeatherEntity) = WeatherResponseDto(
        id = weatherEntity.id,
        date = weatherEntity.date,
        lat = weatherEntity.lat,
        lon = weatherEntity.lon,
        city = weatherEntity.city,
        state = weatherEntity.state,
        temperatures = weatherEntity.temperatures
    )

}