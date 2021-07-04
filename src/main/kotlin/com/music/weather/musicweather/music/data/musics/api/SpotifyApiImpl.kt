package com.music.weather.musicweather.music.data.musics.api

import com.music.weather.musicweather.music.domain.entities.Music
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.util.*

@Component
class SpotifyApiImpl : MusicApi {

    @Value("\${spotify.client.id}")
    private lateinit var CLIENTE_ID: String
    @Value("\${spotify.secret.key}")
    private lateinit var SECRET : String

    override fun authenticate() : String {
        val authorizationToken = Base64.getEncoder().encodeToString("${CLIENTE_ID}:${SECRET}".toByteArray())
        val request = khttp.post("https://accounts.spotify.com/api/token",
                data = mapOf("grant_type" to "client_credentials"),
                headers = mapOf(
                        "Authorization" to "Basic $authorizationToken",
                        "Content-Type" to "application/x-www-form-urlencoded")
                )
        return request.jsonObject.getString("access_token")
    }

    @Cacheable("musics")
    override fun getMusicsByGenre(genre: String): List<Music> {
        val token = authenticate()
        val request = khttp.get("https://api.spotify.com/v1/search",
                params = mapOf("q" to genre, "type" to "track", "limit" to "50"),
                headers = mapOf("Authorization" to "Bearer $token",
                        "Content-Type" to "application/json")
        )

        val musicsJson = request.jsonObject.getJSONObject("tracks").getJSONArray("items")
        val musics  = ArrayList<Music>()
        for(i in 0 until musicsJson.length()){
            val musicJson = musicsJson.getJSONObject(i)
            val url = musicJson.getJSONObject("external_urls").getString("spotify")
            musics.add(Music(musicJson.getString("name"), url))
        }

        return musics
    }
}