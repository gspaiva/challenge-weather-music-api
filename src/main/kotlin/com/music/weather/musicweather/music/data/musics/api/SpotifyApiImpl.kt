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
        val requestPlaylists = khttp.get("https://api.spotify.com/v1/search",
                params = mapOf("q" to genre, "type" to "playlist", "limit" to "50"),
                headers = mapOf("Authorization" to "Bearer $token",
                        "Content-Type" to "application/json")
        )

        val playlistsJson = requestPlaylists.jsonObject.getJSONObject("playlists").getJSONArray("items")
        val idFirstPlaylist = playlistsJson.getJSONObject(0).getString("id")

        val requestTracks = khttp.get("https://api.spotify.com/v1/playlists/${idFirstPlaylist}/tracks",
            params = mapOf("market" to "BR", "limit" to "100"),
            headers = mapOf("Authorization" to "Bearer $token",
                "Content-Type" to "application/json")
        )

        val items = requestTracks.jsonObject.getJSONArray("items")
        val musics  = ArrayList<Music>()
        for(i in 0 until items.length()){
            val track = items.getJSONObject(i).getJSONObject("track")

            val url = track.getJSONObject("external_urls").getString("spotify")
            musics.add(Music(track.getString("name"), url))
        }

        return musics
    }
}