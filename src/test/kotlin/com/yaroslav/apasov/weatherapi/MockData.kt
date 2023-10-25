package com.yaroslav.apasov.weatherapi

import com.yaroslav.apasov.weatherapi.dto.request.WeatherRequestDto
import com.yaroslav.apasov.weatherapi.dto.response.WeatherResponseDto
import com.yaroslav.apasov.weatherapi.entity.WeatherEntity

class MockData {
    companion object {
        fun getValidWeatherList(): List<WeatherEntity> {
            return listOf(
                WeatherEntity.Builder()
                    .setCity("Minsk")
                    .setDate("2001-05-15")
                    .setLat(32.36)
                    .setLon(12.01)
                    .setId(1)
                    .setTemperatures(doubleArrayOf(2.32, 1.24).toList())
                    .build(),
                WeatherEntity.Builder()
                    .setCity("London")
                    .setDate("2001-05-15")
                    .setLat(12.36)
                    .setLon(72.01)
                    .setId(2)
                    .setTemperatures(doubleArrayOf(1.32, 6.24).toList())
                    .build()
            )
        }

        fun getValidWeatherRequestDto(): WeatherRequestDto {
            return WeatherRequestDto(
                date = "2021-11-10",
                lon = 21.23,
                lat = -12.54,
                city = "London",
                state = "London",
                temperatures = doubleArrayOf(2.1,3.5).toList()
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
                temperatures = doubleArrayOf(2.1,3.5).toList()
            )
        }
    }
}

