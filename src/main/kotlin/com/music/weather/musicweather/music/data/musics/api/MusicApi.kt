package com.music.weather.musicweather.music.data.musics.api

import com.music.weather.musicweather.music.domain.entities.Music

interface MusicApi {
    fun authenticate() : String
    fun getMusicsByGenre(genre : String) : List<Music>
}