package com.music.weather.musicweather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2


@SpringBootApplication
class MusicweatherApplication

fun main(args: Array<String>) {
	runApplication<MusicweatherApplication>(*args)
}
