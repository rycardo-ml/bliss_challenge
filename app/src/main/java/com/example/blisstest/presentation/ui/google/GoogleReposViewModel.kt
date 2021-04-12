package com.example.blisstest.presentation.ui.google

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.blisstest.presentation.ui.google.pagination.GoogleReposDataSource
import com.example.blisstest.presentation.ui.google.pagination.PaginationStatus
import com.example.blisstest.util.model.GoogleRepos
import com.example.blisstest.repository.GoogleReposRepository
import com.example.blisstest.util.di.hilt.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainScope
import javax.inject.Inject

@HiltViewModel
class GoogleReposViewModel @Inject constructor(
    val repository: GoogleReposRepository,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val searchPagedListLiveData = initializeSearchListLiveData()
    private var searchDataSource: GoogleReposDataSource? = null

    private val _paginationStatusLiveData = MutableLiveData<PaginationStatus>()
    val paginationStatusLiveData: LiveData<PaginationStatus> get() = _paginationStatusLiveData

    private fun initializeSearchListLiveData(): LiveData<PagedList<GoogleRepos>> {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setPrefetchDistance(20)
            .setEnablePlaceholders(false)
            .build()

        val dataSource = object : DataSource.Factory<Int, GoogleRepos>() {
            override fun create(): GoogleReposDataSource {
                return GoogleReposDataSource(
                    repository = repository,
                    scope = viewModelScope,
                    ioDispatcher,
                    _paginationStatusLiveData
                ).also {
                    searchDataSource = it
                }
            }
        }

        return LivePagedListBuilder(dataSource, config).build()
    }
}
