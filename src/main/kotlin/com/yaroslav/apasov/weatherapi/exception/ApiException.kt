package com.yaroslav.apasov.weatherapi.exception
import org.springframework.http.HttpStatus

const val WEATHER_NOT_FOUND_BY_ID = "Weather with given id %s has not been found"
const val WEATHER_NOT_FOUND = "Weather has not been found"
const val USER_EMAIL_NOT_FOUND = "User with given email has been not found"
const val USER_ID_NOT_FOUND = "User with given id has been not found"

class ApiException(val httpStatus: HttpStatus, message: String) : RuntimeException(message) {
}