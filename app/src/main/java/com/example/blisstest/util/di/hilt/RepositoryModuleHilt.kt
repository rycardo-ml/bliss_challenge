package com.example.blisstest.util.di.hilt

import com.example.blisstest.repository.GoogleReposRepository
import com.example.blisstest.repository.MainRepository
import com.example.blisstest.repository.UserRepository
import com.example.blisstest.util.database.dao.EmojiDao
import com.example.blisstest.util.database.dao.UserDao
import com.example.blisstest.util.database.preferences.PreferenceHandler
import com.example.blisstest.util.network.GitHubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModuleHilt {

    @ActivityRetainedScoped
    @Provides
    fun provideEmoji(apiService: GitHubService,
                     emojiDao: EmojiDao,
                     preferenceHandler: PreferenceHandler
    ): MainRepository = MainRepository(apiService, emojiDao, preferenceHandler)

    @ActivityRetainedScoped
    @Provides
    fun provideUser(apiService: GitHubService,
                     dao: UserDao,
    ): UserRepository = UserRepository(apiService, dao)

    @ActivityRetainedScoped
    @Provides
    fun provideGoogleRepos(apiService: GitHubService): GoogleReposRepository = GoogleReposRepository(apiService)
}