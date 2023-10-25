package com.yaroslav.apasov.weatherapi.repository.specification

import com.yaroslav.apasov.weatherapi.entity.WeatherEntity
import org.springframework.data.jpa.domain.Specification

class WeatherSpecification {

    fun dateEquals(date: String?): Specification<WeatherEntity> {
        return Specification { root, _, builder ->
            builder.equal(root.get<List<WeatherEntity>>("date"), date)
        }
    }

    fun cityIn(cities: List<String>?): Specification<WeatherEntity> {
        return Specification<WeatherEntity> { root, _, builder ->
            builder.and(root.get<List<WeatherEntity>>("city").`in`(cities))
        }
    }

}
