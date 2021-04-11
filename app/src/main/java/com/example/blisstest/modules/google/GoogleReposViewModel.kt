package com.example.blisstest.modules.google

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.blisstest.modules.google.pagination.GoogleReposDataSource
import com.example.blisstest.util.data.model.GoogleRepos
import com.example.blisstest.util.data.repository.GoogleReposRepository
import com.example.blisstest.util.data.repository.UserRepository
import kotlinx.coroutines.MainScope


class GoogleReposViewModel : ViewModel() {

    private val repository = GoogleReposRepository()

    private val viewModelScope = MainScope()

    val searchPagedListLiveData = initializeSearchListLiveData()
    private var searchDataSource: GoogleReposDataSource? = null

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
                    scope = viewModelScope
                ).also {
                    searchDataSource = it
                }
            }
        }

        return LivePagedListBuilder(dataSource, config).build()
    }


}