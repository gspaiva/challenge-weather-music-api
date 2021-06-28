package com.music.weather.musicweather.music.domain.service

import com.music.weather.musicweather.music.data.dto.MusicWeatherDTO
import com.music.weather.musicweather.music.data.musics.api.MusicApi
import com.music.weather.musicweather.music.data.weather.api.WeatherApi
import com.music.weather.musicweather.music.domain.entities.City
import com.music.weather.musicweather.music.domain.entities.Location
import com.music.weather.musicweather.music.domain.entities.Music
import com.music.weather.musicweather.music.domain.entities.Weather
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SearchMusicService @Autowired constructor(private val weatherApi : WeatherApi, private val musicApi : MusicApi) {
    fun searchMusicsByCity(city : City) : MusicWeatherDTO{
        val temperature = weatherApi.getTemperatureByCity(city)
        return getMusicsFromTemperature(temperature)
    }

    fun searchMusicsByLocation(location : Location) : MusicWeatherDTO{
        val temperature = weatherApi.getTemperatureByLocation(location)
        return getMusicsFromTemperature(temperature)
    }

    private fun getMusicsFromTemperature (temperature: Float?): MusicWeatherDTO{

        if(temperature == null){
            return MusicWeatherDTO(Weather(null), listOf())
        }

        return when {
            temperature > 30 -> MusicWeatherDTO(Weather(temperature), musicApi.getMusicsByGenre("party"))
            temperature in 15.0..30.0 -> MusicWeatherDTO(Weather(temperature), musicApi.getMusicsByGenre("pop"))
            temperature in 10.0..14.0 -> MusicWeatherDTO(Weather(temperature), musicApi.getMusicsByGenre("rock"))
            else -> MusicWeatherDTO(Weather(temperature), musicApi.getMusicsByGenre("classical"))
        }
    }
}