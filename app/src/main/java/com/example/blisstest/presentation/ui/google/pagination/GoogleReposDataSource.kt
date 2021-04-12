package com.example.blisstest.presentation.ui.google.pagination

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.blisstest.util.model.GoogleRepos
import com.example.blisstest.repository.GoogleReposRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "GoogleReposDataSource"
class GoogleReposDataSource(
    private val repository: GoogleReposRepository,
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher,
    private val paginationStatusLiveData: MutableLiveData<PaginationStatus>
): PageKeyedDataSource<Int, GoogleRepos>() {

    private var page = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GoogleRepos>) {
        Log.d(TAG, "loadInitial $page")
        scope.launch(dispatcher) {

            paginationStatusLiveData.postValue(PaginationStatus.Loading)

            try {
                val repositories = repository.getRepositories(page)

                if (repositories.isEmpty()) return@launch
                callback.onResult(repositories.toMutableList(), null, ++page)
            } catch (throwable: Throwable) {
                handleError(throwable.message ?: "Failed to fetch repositories")
                return@launch
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GoogleRepos>) {

        Log.d(TAG, "loadInitial $page")

        scope.launch(dispatcher) {

            try {
                val repositories = repository.getRepositories(page)
                if (repositories.isEmpty()) return@launch

                callback.onResult(repositories.toMutableList(), ++page)
            } catch (throwable: Throwable) {
                handleError(throwable.message ?: "Failed to fetch repositories")
                return@launch
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GoogleRepos>) {
    }

    private fun handleError(exception: String) {
        paginationStatusLiveData.postValue(PaginationStatus.Error(exception))
    }
}