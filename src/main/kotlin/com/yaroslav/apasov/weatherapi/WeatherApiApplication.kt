package com.yaroslav.apasov.weatherapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeatherApiApplication

fun main(args: Array<String>) {
	runApplication<WeatherApiApplication>(*args)
}
