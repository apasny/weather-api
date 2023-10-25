package com.yaroslav.apasov.weatherapi.dto.response

data class WeatherResponseDto(
    val id: Long?,
    val date: String?,
    val lat: Double?,
    val lon: Double?,
    val city: String?,
    val state: String?,
    val temperatures: List<Double>?
)
