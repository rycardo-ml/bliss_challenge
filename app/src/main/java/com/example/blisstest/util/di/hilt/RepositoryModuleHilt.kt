package com.example.blisstest.util.di.hilt

import com.example.blisstest.repository.GoogleReposRepository
import com.example.blisstest.repository.EmojiRepository
import com.example.blisstest.repository.UserRepository
import com.example.blisstest.util.database.dao.AppDatabase
import com.example.blisstest.util.database.dao.EmojiDao
import com.example.blisstest.util.database.dao.UserDao
import com.example.blisstest.util.database.preferences.PreferenceHandler
import com.example.blisstest.util.network.GitHubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModuleHilt {

    @ActivityRetainedScoped
    @Provides
    fun provideEmoji(apiService: GitHubService,
                     db: AppDatabase,
                     preferenceHandler: PreferenceHandler,
                     @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): EmojiRepository = EmojiRepository(apiService, db, preferenceHandler, ioDispatcher)

    @ActivityRetainedScoped
    @Provides
    fun provideUser(apiService: GitHubService,
                     db: AppDatabase,
    ): UserRepository = UserRepository(apiService, db)

    @ActivityRetainedScoped
    @Provides
    fun provideGoogleRepos(apiService: GitHubService): GoogleReposRepository = GoogleReposRepository(apiService)
}