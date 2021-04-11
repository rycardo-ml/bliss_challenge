package com.example.blisstest.modules.google.pagination

import androidx.paging.PageKeyedDataSource
import com.example.blisstest.util.data.model.GoogleRepos
import com.example.blisstest.util.data.repository.GoogleReposRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class GoogleReposDataSource(val repository: GoogleReposRepository, private val scope: CoroutineScope): PageKeyedDataSource<Int, GoogleRepos>() {

    private var page = 1;

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GoogleRepos>) {

        scope.launch {
            val users = repository.getRepositories(page)

            if (users.isEmpty()) return@launch

            callback.onResult(users.toMutableList(), null, ++page)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GoogleRepos>) {
        scope.launch {
            val users = repository.getRepositories(params.key)

            if (users.isEmpty()) return@launch

            callback.onResult(users.toMutableList(), ++page)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GoogleRepos>) {
    }
}