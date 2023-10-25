package com.yaroslav.apasov.weatherapi.entity

import jakarta.persistence.*

@Entity
@Table(name = "weather")
data class WeatherEntity(
    @Id
    @SequenceGenerator(name = "weather_sequence", sequenceName = "weather_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_sequence")
    @Column(name = "id")
    val id: Long?,

    @Column(name = "date")
    val date: String?,

    @Column(name = "lat")
    val lat: Double?,

    @Column(name = "lon")
    val lon: Double?,

    @Column(name = "city")
    val city: String?,

    @Column(name = "state")
    val state: String?,

    @ElementCollection
    @CollectionTable(
        name = "weather_temperatures",
        joinColumns = [JoinColumn(name = "weather_id")]
    )
    @Column(name = "temperature")
    val temperatures: List<Double>?,
) {

    data class Builder(
        private var id: Long? = null,
        private var date: String? = null,
        private var lat: Double? = null,
        private var lon: Double? = null,
        private var city: String? = null,
        private var state: String? = null,
        private var temperatures: ArrayList<Double>? = null
    ) {

        fun setId(id: Long) = apply {
            this.id = id
        }

        fun setDate(date: String) = apply {
            this.date = date
        }

        fun setCity(city: String) = apply {
            this.city = city
        }

        fun setState(state: String) = apply {
            this.state = state
        }

        fun setLat(lat: Double) = apply {
            this.lat = lat
        }

        fun setLon(lon: Double) = apply {
            this.lon = lon
        }

        fun setTemperatures(temperatures: List<Double>) = apply {
            this.temperatures = temperatures as ArrayList<Double>
        }

        fun build(): WeatherEntity {
            return WeatherEntity(id, date, lat, lon, city, state, temperatures)
        }
    }
}
