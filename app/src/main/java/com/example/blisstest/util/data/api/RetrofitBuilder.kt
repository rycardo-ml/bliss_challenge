package com.example.blisstest.util.data.api

import android.util.Log
import com.example.blisstest.util.data.api.converter.EmojiConverterFactory
import com.example.blisstest.util.data.api.converter.TypeTokenCreator
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val TAG = "RetrofitBuilder"
    private const val BASE_URL = "https://api.github.com/"

    private fun getRetrofit(): Retrofit {
        Log.d(TAG, "create Retrofit")

        val gson =
            GsonBuilder()
                .registerTypeAdapter(TypeTokenCreator.createListEmoji(), EmojiConverterFactory())
                .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: GitHubService = getRetrofit().create(GitHubService::class.java)
}