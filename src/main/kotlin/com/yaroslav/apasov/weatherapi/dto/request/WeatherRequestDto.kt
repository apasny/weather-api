package com.yaroslav.apasov.weatherapi.dto.request

data class WeatherRequestDto
constructor(
    val date: String,
    val lat: Double,
    val lon: Double,
    val city: String,
    val state: String,
    val temperatures: List<Double>
)
