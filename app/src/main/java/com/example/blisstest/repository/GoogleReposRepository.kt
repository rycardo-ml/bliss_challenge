package com.example.blisstest.repository

import android.util.Log
import com.example.blisstest.util.network.GitHubService
import com.example.blisstest.util.model.GoogleRepos

private const val TAG = "GoogleReposRepository"
class GoogleReposRepository(private val apiService: GitHubService) {

    init {
        Log.d(TAG, "apiService $apiService")
    }

    suspend fun getRepositories(page: Int): List<GoogleRepos> {
        Log.d(TAG, "init getRepositories $page")

        val users = apiService.getGoogleRepos(page)

        Log.d(TAG, "end getRepositories $page")
        return users
    }
}