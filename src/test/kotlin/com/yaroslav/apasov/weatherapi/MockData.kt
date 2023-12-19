package com.yaroslav.apasov.weatherapi

import com.yaroslav.apasov.weatherapi.dto.request.WeatherRequestDto
import com.yaroslav.apasov.weatherapi.dto.response.WeatherResponseDto
import com.yaroslav.apasov.weatherapi.entity.WeatherEntity

class MockData {
    companion object {
        fun getValidWeatherList(): List<WeatherEntity> {
            return listOf(
                WeatherEntity(1, "2001-05-15", 32.36, 12.01, "Minsk", "Minsk", doubleArrayOf(2.32, 1.24).toList()),
                WeatherEntity(2, "2001-05-15", 12.36, 72.01, "London", "London", doubleArrayOf(1.32, 6.24).toList())
            )
        }

        fun getValidWeatherRequestDto(): WeatherRequestDto {
            return WeatherRequestDto(
                date = "2021-11-10",
                lon = 21.23,
                lat = -12.54,
                city = "London",
                state = "London",
                temperatures = doubleArrayOf(2.1, 3.5).toList()
            )
        }

        fun getValidWeatherResponseDto(): WeatherResponseDto {
            return WeatherResponseDto(
                id = 1,
                date = "2021-11-10",
                lon = 21.23,
                lat = -12.54,
                city = "London",
                state = "London",
                temperatures = doubleArrayOf(2.1, 3.5).toList()
            )
        }
    }
}

