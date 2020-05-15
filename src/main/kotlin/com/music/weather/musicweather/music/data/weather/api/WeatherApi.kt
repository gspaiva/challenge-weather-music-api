package com.music.weather.musicweather.music.data.weather.api

import com.music.weather.musicweather.music.domain.entities.City

interface WeatherApi {
    fun getTemperatureByCity(city : City) : Float
}