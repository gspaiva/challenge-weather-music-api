package com.music.weather.musicweather.music.controllers

import com.music.weather.musicweather.music.data.dto.MusicWeatherDTO
import com.music.weather.musicweather.music.domain.entities.City
import com.music.weather.musicweather.music.domain.entities.Location
import com.music.weather.musicweather.music.domain.service.SearchMusicService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/music")
@Api("Music by city API")
class MusicController @Autowired constructor(val searchMusicService: SearchMusicService){

    @GetMapping("/city")
    @ApiOperation("Given a city name search a music playlist to your city based on weather")
    fun searchMusicByCityWeather(@RequestParam("name") name : String): ResponseEntity<MusicWeatherDTO>{
        return try{
            val city = City(name.toLowerCase())
            ResponseEntity.ok().body(searchMusicService.searchMusicsByCity(city))
        } catch (exception: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/city/coordinates")
    @ApiOperation("Given a coordinates search a music playlist to your city based on weather")
    fun searchMusicByLongitudeAndLatitude(@RequestParam lat : Float, @RequestParam lon : Float): ResponseEntity<MusicWeatherDTO>{
        val location = Location(lat, lon)
        return ResponseEntity.ok().body(searchMusicService.searchMusicsByLocation(location))
    }
}