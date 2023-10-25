package com.yaroslav.apasov.weatherapi.unit

import com.yaroslav.apasov.weatherapi.MockData
import com.yaroslav.apasov.weatherapi.exception.ApiException
import com.yaroslav.apasov.weatherapi.mapper.WeatherMapper
import com.yaroslav.apasov.weatherapi.repository.WeatherRepository
import com.yaroslav.apasov.weatherapi.service.impl.WeatherServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Sort

@ExtendWith(MockitoExtension::class)
class WeatherServiceImplTest {

    private val weatherRepositoryMock: WeatherRepository = mockk()
    private val mapper: WeatherMapper = spyk()
    private val myClass = WeatherServiceImpl(weatherRepositoryMock, mapper)

    @Test
    fun whenGetAllWeather_shouldSuccess(){
        val expected = MockData.getValidWeatherList().map { w-> mapper.toWeatherResponseDto(w) }
        every { weatherRepositoryMock.findAll(any(),any(Sort::class)) } returns MockData.getValidWeatherList()
        val result = myClass.getAllWeather(null,null,null)
        assertEquals(expected, result)
    }

    @Test
    fun whenGetAllWeather_shouldFail(){
        every { weatherRepositoryMock.findAll(any(),any(Sort::class)) } returns emptyList()
        assertThrows<ApiException> { myClass.getAllWeather(null,null,null) }
    }

}