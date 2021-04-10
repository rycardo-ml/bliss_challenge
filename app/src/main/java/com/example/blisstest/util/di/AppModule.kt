package com.example.blisstest.util.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.blisstest.BlissApplication
import com.example.blisstest.util.data.api.GitHubService
import com.example.blisstest.util.data.api.RetrofitBuilder
import com.example.blisstest.util.data.dao.AppDatabase
import com.example.blisstest.util.preferences.PreferenceHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule() {

    @Singleton
    @Provides
    fun provideGitHubApi(): GitHubService = RetrofitBuilder.apiService

    @Singleton
    @Provides
    fun providePreferences(): PreferenceHandler {
        return PreferenceHandler(PreferenceManager.getDefaultSharedPreferences(BlissApplication.instance))
    }

    @Singleton
    @Provides
    fun provideRoom(): AppDatabase = AppDatabase.invoke(BlissApplication.instance)

    @Provides
    fun provideContext(): Context = BlissApplication.instance

}