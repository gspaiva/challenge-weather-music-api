package com.music.weather.musicweather.music.data.musics.api

import com.music.weather.musicweather.music.domain.entities.Playlist
import org.springframework.beans.factory.annotation.Value
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

    override fun getPlaylistByGenre(genre: String): List<Playlist> {
        val token = authenticate()
        val request = khttp.get("https://api.spotify.com/v1/search",
                params = mapOf("q" to genre, "type" to "playlist"),
                headers = mapOf("Authorization" to "Bearer $token",
                        "Content-Type" to "application/json")
        )

        val playlistsJson = request.jsonObject.getJSONObject("playlists").getJSONArray("items")
        var playlists  = ArrayList<Playlist>()
        for(i in 0 until playlistsJson.length()){
            val playlistJson = playlistsJson.getJSONObject(i)
            val openSpotifyUrl = playlistJson.getJSONObject("external_urls").getString("spotify")
            val playlist = Playlist(playlistJson.getString("name"), openSpotifyUrl, "Spotify")
            playlists.add(playlist)
        }

        return playlists
    }
}