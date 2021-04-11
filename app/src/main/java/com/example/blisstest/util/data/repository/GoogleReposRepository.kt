package com.example.blisstest.util.data.repository

import android.util.Log
import com.example.blisstest.util.data.api.GitHubService
import com.example.blisstest.util.data.dao.AppDatabase
import com.example.blisstest.util.data.dao.EmojiDao
import com.example.blisstest.util.data.dao.UserDao
import com.example.blisstest.util.data.model.GoogleRepos
import com.example.blisstest.util.data.model.User
import com.example.blisstest.util.di.DaggerAppComponent
import javax.inject.Inject

private const val TAG = "GoogleReposRepository"
class GoogleReposRepository {

    @Inject
    lateinit var apiService: GitHubService

    init {
        DaggerAppComponent.create().inject(this)

        Log.d(TAG, "apiService $apiService")
    }

    suspend fun getRepositories(page: Int): List<GoogleRepos> {
        Log.d(TAG, "init getRepositories $page")

        val users = apiService.getGoogleRepos(page)

        Log.d(TAG, "end getRepositories $page")
        return users
    }
}