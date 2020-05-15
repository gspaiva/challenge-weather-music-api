package com.music.weather.musicweather.music.domain.service

import com.music.weather.musicweather.music.data.weather.api.WeatherApi
import com.music.weather.musicweather.music.domain.entities.City
import com.music.weather.musicweather.music.domain.entities.Music
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class SearchMusicService @Autowired constructor(private val weatherApi : WeatherApi ) {
    fun searchPlaylistByCity(city : City) : List<Music>?{
        val temperature = weatherApi.getTemperatureByCity(city)
        return ArrayList()
    }
}