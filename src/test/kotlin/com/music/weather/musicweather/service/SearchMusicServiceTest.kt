package com.music.weather.musicweather.service

import com.music.weather.musicweather.music.data.musics.api.MusicApi
import com.music.weather.musicweather.music.data.weather.api.WeatherApi
import com.music.weather.musicweather.music.domain.entities.City
import com.music.weather.musicweather.music.domain.entities.Location
import com.music.weather.musicweather.music.domain.entities.Music
import com.music.weather.musicweather.music.domain.service.SearchMusicService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SearchMusicServiceTest {

    val weatherApi: WeatherApi = mockk()
    val musicApi: MusicApi = mockk()

    val searchMusicService = SearchMusicService(
        weatherApi,
        musicApi
    )

    @Test
    fun shouldReturnTemperatureAndMusicsSuccessfullyWhenCityTemperatureIsFound(){
        every { weatherApi.getTemperatureByCity(any()) } returns 10f
        every { musicApi.getMusicsByGenre(any()) } returns listOf(
            Music("Music test", "url-fake"),
            Music("Music test2", "url-fake")
        )
        val musicsDto = searchMusicService.searchMusicsByCity(City("varginha"))
        Assertions.assertEquals(2, musicsDto.musics.size)
        Assertions.assertEquals(musicsDto.weather.temperature, 10f)
        Assertions.assertEquals(musicsDto.musics[0].name, "Music test")
        Assertions.assertEquals(musicsDto.musics[1].name, "Music test2")
    }

    @Test
    fun shouldReturnTemperatureAndMusicsSuccessfullyWhenLocationTemperatureIsFound(){
        every { weatherApi.getTemperatureByLocation(any()) } returns 10f
        every { musicApi.getMusicsByGenre(any()) } returns listOf(
            Music("Music test", "url-fake"),
            Music("Music test2", "url-fake")
        )
        val musicsDto = searchMusicService.searchMusicsByLocation(Location(-21.2121f, -43.2121f))
        Assertions.assertEquals(2, musicsDto.musics.size)
        Assertions.assertEquals(musicsDto.weather.temperature, 10f)
        Assertions.assertEquals(musicsDto.musics[0].name, "Music test")
        Assertions.assertEquals(musicsDto.musics[1].name, "Music test2")
    }

    @Test
    fun shouldReturnRockGenreMusicsWhenTemperatureIsBetween10And14(){
        every { weatherApi.getTemperatureByCity(any()) } returns 10f
        every { musicApi.getMusicsByGenre(any()) } returns listOf(
            Music("Music test", "url-fake"),
            Music("Music test2", "url-fake")
        )
        val musicsDto = searchMusicService.searchMusicsByCity(City("varginha"))
        Assertions.assertEquals("rock", musicsDto.musicsGenre)
    }

    @Test
    fun shouldReturnPartyGenreMusicsWhenTemperatureIsGreaterThan30(){
        every { weatherApi.getTemperatureByCity(any()) } returns 31f
        every { musicApi.getMusicsByGenre(any()) } returns listOf(
            Music("Music test", "url-fake"),
            Music("Music test2", "url-fake")
        )
        val musicsDto = searchMusicService.searchMusicsByCity(City("varginha"))
        Assertions.assertEquals("party", musicsDto.musicsGenre)
    }

    @Test
    fun shouldReturnPopGenreMusicsWhenTemperatureIsBetween15And30(){
        every { weatherApi.getTemperatureByCity(any()) } returns 15f
        every { musicApi.getMusicsByGenre(any()) } returns listOf(
            Music("Music test", "url-fake"),
            Music("Music test2", "url-fake")
        )
        val musicsDto = searchMusicService.searchMusicsByCity(City("varginha"))
        Assertions.assertEquals("pop", musicsDto.musicsGenre)
    }

    @Test
    fun shouldReturnClassicalGenreMusicsWhenTemperatureIsLessThan10(){
        every { weatherApi.getTemperatureByCity(any()) } returns 9.54f
        every { musicApi.getMusicsByGenre(any()) } returns listOf(
            Music("Music test", "url-fake"),
            Music("Music test2", "url-fake")
        )
        val musicsDto = searchMusicService.searchMusicsByCity(City("varginha"))
        Assertions.assertEquals("classical", musicsDto.musicsGenre)
    }
}