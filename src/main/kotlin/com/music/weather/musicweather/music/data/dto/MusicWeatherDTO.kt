package com.music.weather.musicweather.music.data.dto

import com.music.weather.musicweather.music.domain.entities.Music
import com.music.weather.musicweather.music.domain.entities.Weather

data class MusicWeatherDTO (
    val weather: Weather,
    val musics: List<Music>,
    val musicsGenre: String
        )