package com.example.blisstest.util.data.api

import com.example.blisstest.util.data.model.Emoji
import com.example.blisstest.util.data.model.GoogleRepos
import com.example.blisstest.util.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// TODO in the google repos request the size param doesnt work

//https://api.github.com/emojis
//https://api.github.com/users/rycardo-ml
//https://api.github.com/users/google/repos?page=1&size=5

interface GitHubService {

    @GET("emojis")
    suspend fun getEmojis(): List<Emoji>

    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): User

    @GET("users/google/repos")
    suspend fun getGoogleRepos(@Query("page") page: Int): List<GoogleRepos>

}