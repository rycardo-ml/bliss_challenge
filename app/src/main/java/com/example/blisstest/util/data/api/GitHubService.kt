package com.example.blisstest.util.data.api

import com.example.blisstest.util.data.model.Emoji
import retrofit2.http.GET


//https://api.github.com/emojis

interface GitHubService {

    @GET("emojis")
    suspend fun getEmojis(): List<Emoji>


}