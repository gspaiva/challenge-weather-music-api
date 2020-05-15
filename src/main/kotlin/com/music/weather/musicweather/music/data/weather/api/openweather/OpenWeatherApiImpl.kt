package com.music.weather.musicweather.music.data.weather.api.openweather

import com.music.weather.musicweather.music.data.weather.api.WeatherApi
import com.music.weather.musicweather.music.domain.entities.City
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OpenWeatherApiImpl : WeatherApi {

    @Value("\${openweather.app.id}")
    private lateinit var APP_ID : String
    private val API_URL = "http://api.openweathermap.org/data/2.5/weather"
    private val UNITS = "metric"

    override fun getTemperatureByCity(city: City): Float {
        val request = khttp.get(API_URL, params = mapOf("q" to city.name, "units" to UNITS, "appid" to APP_ID))
        val json = request.jsonObject

        return json.getJSONObject("main").getDouble("temp").toFloat()
    }
}