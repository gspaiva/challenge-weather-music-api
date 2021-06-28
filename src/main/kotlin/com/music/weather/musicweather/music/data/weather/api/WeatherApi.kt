package com.music.weather.musicweather.music.data.weather.api

import com.music.weather.musicweather.music.domain.entities.City
import com.music.weather.musicweather.music.domain.entities.Location

interface WeatherApi {
    fun getTemperatureByCity(city : City) : Float?
    fun getTemperatureByLocation(location : Location) : Float
}