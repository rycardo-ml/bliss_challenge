package com.example.blisstest.util.di.hilt

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.blisstest.util.database.dao.AppDatabase
import com.example.blisstest.util.database.dao.EmojiDao
import com.example.blisstest.util.database.dao.UserDao
import com.example.blisstest.util.database.preferences.PreferenceHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModuleHilt {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext app: Context): PreferenceHandler {
        return PreferenceHandler(PreferenceManager.getDefaultSharedPreferences(app))
    }

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext app: Context): AppDatabase = AppDatabase.invoke(app)
}