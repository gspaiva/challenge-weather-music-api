package com.music.weather.musicweather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import springfox.documentation.swagger2.annotations.EnableSwagger2


@SpringBootApplication
@EnableCaching
class MusicweatherApplication

fun main(args: Array<String>) {
	runApplication<MusicweatherApplication>(*args)
}
