package com.yaroslav.apasov.weatherapi.controller

import com.yaroslav.apasov.weatherapi.dto.request.WeatherRequestDto
import com.yaroslav.apasov.weatherapi.dto.response.WeatherResponseDto
import com.yaroslav.apasov.weatherapi.entity.WeatherEntity
import com.yaroslav.apasov.weatherapi.service.WeatherService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

const val API_URI: String = "/api/v1"
const val WEATHER_URI: String = "/weather"
const val WEATHER_ID_URI: String = "/{id}"
const val WEATHER_URL: String = API_URI.plus(WEATHER_URI)

@RestController
@RequestMapping(WEATHER_URL)
class WeatherController(
    private val weatherService: WeatherService,
) {
    @GetMapping
    fun getAllWeather(
        @RequestParam(name = "date", required = false) date: String?,
        @RequestParam(name = "city", required = false) cities: List<String>?,
        @RequestParam(name = "sort", required = false) sort: String?
    ): List<WeatherResponseDto> {
        return weatherService.getAllWeather(date, cities, sort)
    }

    @GetMapping(WEATHER_ID_URI)
    fun getWeatherById(@PathVariable id:Long): WeatherResponseDto {
        return weatherService.getWeatherById(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createWeather(@RequestBody weatherRequestDto: WeatherRequestDto): WeatherResponseDto {
        return weatherService.createWeather(weatherRequestDto)
    }

}