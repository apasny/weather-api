package com.yaroslav.apasov.weatherapi.mapper

import com.yaroslav.apasov.weatherapi.dto.request.WeatherRequestDto
import com.yaroslav.apasov.weatherapi.dto.response.WeatherResponseDto
import com.yaroslav.apasov.weatherapi.entity.WeatherEntity
import org.springframework.stereotype.Service

@Service
class WeatherMapper {

    fun toWeatherResponseDto(weatherEntity: WeatherEntity) = WeatherResponseDto(
        id = weatherEntity.getId(),
        date = weatherEntity.getDate(),
        lat = weatherEntity.getLat(),
        lon = weatherEntity.getLon(),
        city = weatherEntity.getCity(),
        state = weatherEntity.getState(),
        temperatures = weatherEntity.getTemperatures()
    )

    fun toWeatherEntity(weatherRequestDto: WeatherRequestDto) = WeatherEntity(
        id = null,
        date = weatherRequestDto.date,
        lat = weatherRequestDto.lat,
        lon = weatherRequestDto.lon,
        city = weatherRequestDto.city,
        state = weatherRequestDto.state,
        temperatures = weatherRequestDto.temperatures
    )

}