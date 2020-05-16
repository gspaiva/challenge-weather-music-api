package com.music.weather.musicweather.music.data.musics.api

import com.music.weather.musicweather.music.domain.entities.Playlist

interface MusicApi {
    fun authenticate() : String
    fun getPlaylistByGenre(genre : String) : List<Playlist>
}