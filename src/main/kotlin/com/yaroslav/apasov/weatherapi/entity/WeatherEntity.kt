package com.yaroslav.apasov.weatherapi.entity

import jakarta.persistence.*

@Entity
@Table(name = "weather")
data class WeatherEntity(
    @Id
    @SequenceGenerator(name = "weather_sequence", sequenceName = "weather_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_sequence")
    @Column(name = "id")
    private var id: Long?,

    @Column(name = "date")
    private var date: String?,

    @Column(name = "lat")
    private var lat: Double?,

    @Column(name = "lon")
    private var lon: Double?,

    @Column(name = "city")
    private var city: String?,

    @Column(name = "state")
    private var state: String?,

    @ElementCollection
    @CollectionTable(
        name = "weather_temperatures",
        joinColumns = [JoinColumn(name = "weather_id")]
    )
    @Column(name = "temperature")
    private var temperatures: List<Double>?,
) {

    fun getId():Long? {
        return id
    }
    fun getDate():String? {
        return date
    }

    fun getCity():String? {
        return city
    }

    fun getState():String? {
        return state
    }

    fun getLat():Double? {
        return lat
    }

    fun getLon():Double? {
        return lon
    }

    fun getTemperatures():List<Double>? {
        return temperatures
    }

}
