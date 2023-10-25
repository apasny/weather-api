package com.yaroslav.apasov.weatherapi.integration


import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.yaroslav.apasov.weatherapi.MockData
import com.yaroslav.apasov.weatherapi.controller.WEATHER_URL
import com.yaroslav.apasov.weatherapi.dto.request.WeatherRequestDto
import com.yaroslav.apasov.weatherapi.dto.response.WeatherResponseDto
import com.yaroslav.apasov.weatherapi.exception.WEATHER_NOT_FOUND
import com.yaroslav.apasov.weatherapi.exception.WEATHER_NOT_FOUND_BY_ID
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql(
    scripts = ["classpath:data/weather-initial-data.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class WeatherControllerIntegrationTest {

    @Autowired
    protected var mockMvc: MockMvc? = null

    @Autowired
    protected var objectMapper: ObjectMapper? = null

    @Throws(JsonProcessingException::class)
    protected fun <T> toJson(`object`: T): String? {
        return objectMapper?.writeValueAsString(`object`)
    }

    companion object {
        @Container
        var postgresqlContainer =
            PostgreSQLContainer("postgres:14-alpine")
                .withDatabaseName("weather-service")
                .withUsername("postgres")
                .withPassword("postgres")
                .withReuse(true) as PostgreSQLContainer

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresqlContainer::getUsername)
            registry.add("spring.datasource.password", postgresqlContainer::getPassword)
        }

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            postgresqlContainer.start()
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            postgresqlContainer.stop()
        }
    }

    @Test
    @Throws(Exception::class)
    @Sql(
        scripts = ["classpath:data/empty-database.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    fun shouldFailGetAllWeatherIfEmpty() {
        mockMvc?.get(WEATHER_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }?.andExpect {
            status { isNotFound() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message",`is`(WEATHER_NOT_FOUND))
        }
    }

    @Test
    @Throws(Exception::class)
    fun shouldSuccessGetAllWeatherWithoutFilters() {
        mockMvc?.get(WEATHER_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }?.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$[0].id",`is`(1))
            jsonPath("$[0].date", `is`("2021-11-10"))
            jsonPath("$[0].lon", `is`(21.23))
            jsonPath("$[0].lat", `is`(-12.54))
            jsonPath("$[0].city", `is`("London"))
            jsonPath("$[0].state", `is`("London"))
            jsonPath("$[0].temperatures", `is`(doubleArrayOf(2.1,3.5).toList()))
        }
    }

    @Test
    @Throws(Exception::class)
    fun shouldSuccessGetAllWeatherWithDateFilter() {
        mockMvc?.get(WEATHER_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            param("date", "2020-11-10")
        }?.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$[0].id",`is`(2))
            jsonPath("$[0].date", `is`("2020-11-10"))
            jsonPath("$[0].lon", `is`(51.23))
            jsonPath("$[0].lat", `is`(-32.54))
            jsonPath("$[0].city", `is`("Minsk"))
            jsonPath("$[0].state", `is`("Minsk"))
            jsonPath("$[0].temperatures", `is`(doubleArrayOf(3.1,4.5).toList()))
        }
    }

    @Test
    @Throws(Exception::class)
    fun shouldSuccessGetAllWeatherWithCityFilter() {
        mockMvc?.get(WEATHER_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            param("city", "London, Minsk")
        }?.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$[0].id",`is`(1))
            jsonPath("$[0].date", `is`("2021-11-10"))
            jsonPath("$[0].lon", `is`(21.23))
            jsonPath("$[0].lat", `is`(-12.54))
            jsonPath("$[0].city", `is`("London"))
            jsonPath("$[0].state", `is`("London"))
            jsonPath("$[0].temperatures", `is`(doubleArrayOf(2.1,3.5).toList()))
            jsonPath("$[1].id",`is`(2))
            jsonPath("$[1].date", `is`("2020-11-10"))
            jsonPath("$[1].lon", `is`(51.23))
            jsonPath("$[1].lat", `is`(-32.54))
            jsonPath("$[1].city", `is`("Minsk"))
            jsonPath("$[1].state", `is`("Minsk"))
            jsonPath("$[1].temperatures", `is`(doubleArrayOf(3.1,4.5).toList()))
        }
    }

    @Test
    @Throws(Exception::class)
    fun shouldSuccessGetAllWeatherWithSortFilter() {
        mockMvc?.get(WEATHER_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            param("sort", "date")
        }?.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$[1].id",`is`(1))
            jsonPath("$[1].date", `is`("2021-11-10"))
            jsonPath("$[1].lon", `is`(21.23))
            jsonPath("$[1].lat", `is`(-12.54))
            jsonPath("$[1].city", `is`("London"))
            jsonPath("$[1].state", `is`("London"))
            jsonPath("$[1].temperatures", `is`(doubleArrayOf(2.1,3.5).toList()))
            jsonPath("$[0].id",`is`(2))
            jsonPath("$[0].date", `is`("2020-11-10"))
            jsonPath("$[0].lon", `is`(51.23))
            jsonPath("$[0].lat", `is`(-32.54))
            jsonPath("$[0].city", `is`("Minsk"))
            jsonPath("$[0].state", `is`("Minsk"))
            jsonPath("$[0].temperatures", `is`(doubleArrayOf(3.1,4.5).toList()))
        }
    }

    @Test
    @Throws(Exception::class)
    @Sql(
        scripts = ["classpath:data/empty-database.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    fun shouldSuccessCreateWeatherEntity() {
        val input: WeatherRequestDto = MockData.getValidWeatherRequestDto()
        val expectation: WeatherResponseDto = MockData.getValidWeatherResponseDto()

        mockMvc?.post(WEATHER_URL) {
            contentType = MediaType.APPLICATION_JSON
            content = toJson(input)
            accept = MediaType.APPLICATION_JSON
        }?.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(toJson(expectation).orEmpty()) }
        }
    }

    @Test
    @Throws(Exception::class)
    fun shouldSuccessGetWeatherById() {
        mockMvc?.get(WEATHER_URL.plus("/2") ) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }?.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id",`is`(2))
            jsonPath("$.date", `is`("2020-11-10"))
            jsonPath("$.lon", `is`(51.23))
            jsonPath("$.lat", `is`(-32.54))
            jsonPath("$.city", `is`("Minsk"))
            jsonPath("$.state", `is`("Minsk"))
            jsonPath("$.temperatures", `is`(doubleArrayOf(3.1,4.5).toList()))
        }
    }

    @Test
    @Throws(Exception::class)
    fun shouldFailGetWeatherByIdIfNotFound() {
        mockMvc?.get(WEATHER_URL.plus("/3") ) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }?.andExpect {
            status { isNotFound() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message",`is`(WEATHER_NOT_FOUND_BY_ID.format(3)))
        }
    }


}

