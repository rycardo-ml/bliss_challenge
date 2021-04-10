package com.example.blisstest.util.data.api

import com.example.blisstest.util.data.model.Emoji
import com.example.blisstest.util.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path


//https://api.github.com/emojis
//https://api.github.com/users/rycardo-ml

interface GitHubService {

    @GET("emojis")
    suspend fun getEmojis(): List<Emoji>

    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): User

}