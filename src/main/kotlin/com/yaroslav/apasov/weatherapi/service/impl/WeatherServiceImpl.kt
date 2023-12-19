package com.yaroslav.apasov.weatherapi.service.impl

import com.yaroslav.apasov.weatherapi.dto.request.WeatherRequestDto
import com.yaroslav.apasov.weatherapi.dto.response.WeatherResponseDto
import com.yaroslav.apasov.weatherapi.entity.WeatherEntity
import com.yaroslav.apasov.weatherapi.exception.ApiException
import com.yaroslav.apasov.weatherapi.exception.WEATHER_NOT_FOUND
import com.yaroslav.apasov.weatherapi.exception.WEATHER_NOT_FOUND_BY_ID
import com.yaroslav.apasov.weatherapi.mapper.WeatherMapper
import com.yaroslav.apasov.weatherapi.repository.WeatherRepository
import com.yaroslav.apasov.weatherapi.repository.specification.WeatherSpecification
import com.yaroslav.apasov.weatherapi.service.WeatherService
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

const val DEFAULT_SORT_PROPERTY = "id"
const val SORT_PROPERTY = "date"
const val SORT_PARAM = "-date"

@Service
class WeatherServiceImpl(private val weatherRepository: WeatherRepository, private val mapper: WeatherMapper) :
    WeatherService {

    override fun getAllWeather(date: String?, cities: List<String>?, sort: String?): List<WeatherResponseDto> {
        val spec: Specification<WeatherEntity> =
            Specification
                .where(if (date.isNullOrBlank()) null else WeatherSpecification().dateEquals(date))
                .and(if (cities.isNullOrEmpty()) null else WeatherSpecification().cityIn(cities.map { c ->
                    prepareText(c)
                }.toList()))
        val allWeather = weatherRepository.findAll(spec, Sort.by(getSortingOrders(sort)))
        return if (allWeather.isEmpty()) throw ApiException(
            HttpStatus.NOT_FOUND,
            WEATHER_NOT_FOUND
        ) else allWeather.map { w -> mapper.toWeatherResponseDto(w) }.toList()
    }

    private fun getSortingOrders(sort: String?): List<Sort.Order> {
        val defaultOrder = Sort.Order(Sort.Direction.ASC, DEFAULT_SORT_PROPERTY)
        if (sort == null) return listOf(defaultOrder)
        val orders: MutableList<Sort.Order> = ArrayList()
        if (sort == SORT_PARAM) orders.add(Sort.Order(Sort.Direction.DESC, SORT_PROPERTY).ignoreCase())
            else orders.add(Sort.Order(Sort.Direction.ASC, SORT_PROPERTY).ignoreCase())
        orders.add(defaultOrder)
        return orders
    }

    override fun getWeatherById(id: Long): WeatherResponseDto {
        return mapper.toWeatherResponseDto(weatherRepository.findById(id).orElseThrow {
            ApiException(
                HttpStatus.NOT_FOUND,
                WEATHER_NOT_FOUND_BY_ID.format(id)
            )
        })
    }

    override fun createWeather(weatherRequestDto: WeatherRequestDto): WeatherResponseDto {
        val weatherEntity = mapper.toWeatherEntity(weatherRequestDto)
        return mapper.toWeatherResponseDto(weatherRepository.save(weatherEntity))
    }

    private fun prepareText(text: String): String {
        return text.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}