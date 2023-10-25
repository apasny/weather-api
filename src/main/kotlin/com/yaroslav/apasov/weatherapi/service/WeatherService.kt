package com.yaroslav.apasov.weatherapi.service

import com.yaroslav.apasov.weatherapi.dto.request.WeatherRequestDto
import com.yaroslav.apasov.weatherapi.dto.response.WeatherResponseDto
import com.yaroslav.apasov.weatherapi.entity.WeatherEntity

interface WeatherService {
    fun getAllWeather(date: String?, cities: List<String>?, sort: String?): List<WeatherResponseDto>
    fun getWeatherById(id: Long): WeatherResponseDto
    fun createWeather(weatherRequestDto: WeatherRequestDto): WeatherResponseDto
}