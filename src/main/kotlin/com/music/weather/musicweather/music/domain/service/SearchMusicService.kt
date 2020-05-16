package com.music.weather.musicweather.music.domain.service

import com.music.weather.musicweather.music.data.musics.api.MusicApi
import com.music.weather.musicweather.music.data.weather.api.WeatherApi
import com.music.weather.musicweather.music.domain.entities.City
import com.music.weather.musicweather.music.domain.entities.Music
import com.music.weather.musicweather.music.domain.entities.Playlist
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class SearchMusicService @Autowired constructor(private val weatherApi : WeatherApi, private val musicApi : MusicApi) {
    fun searchPlaylistByCity(city : City) : List<Playlist>?{
        val temperature = weatherApi.getTemperatureByCity(city)

        return when {
            temperature > 30 -> musicApi.getPlaylistByGenre("party")
            temperature in 15.0..30.0 -> musicApi.getPlaylistByGenre("pop")
            temperature in 10.0..14.0 -> musicApi.getPlaylistByGenre("rock")
            else -> musicApi.getPlaylistByGenre("classical")
        }

    }
}