package com.example.blisstest.util.di.hilt

import com.example.blisstest.util.network.GitHubService
import com.example.blisstest.util.network.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleHilt {

    @Singleton
    @Provides
    fun provideGitHubApi(): GitHubService = RetrofitBuilder.apiService

}