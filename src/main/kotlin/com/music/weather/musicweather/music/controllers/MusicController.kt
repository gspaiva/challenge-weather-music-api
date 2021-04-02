package com.music.weather.musicweather.music.controllers

import com.music.weather.musicweather.music.domain.entities.City
import com.music.weather.musicweather.music.domain.entities.Playlist
import com.music.weather.musicweather.music.domain.service.SearchMusicService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/music")
@Api("Music by city API")
class MusicController @Autowired constructor(val searchMusicService: SearchMusicService){

    @GetMapping("/city")
    @ApiOperation("Search a music playlist to your city based on weather")
    fun searchMusicByCityWeather(@RequestParam cityName : String) : List<Playlist>?{
        val city = City(cityName)
        return searchMusicService.searchPlaylistByCity(city)
    }
}