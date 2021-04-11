package com.example.blisstest.util.di.hilt

import com.example.blisstest.presentation.ui.emoji.EmojiUseCase
import com.example.blisstest.presentation.ui.main.MainUseCase
import com.example.blisstest.presentation.ui.user.UserUseCase
import com.example.blisstest.repository.EmojiRepository
import com.example.blisstest.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModuleHilt {

    @ActivityRetainedScoped
    @Provides
    fun provideEmojiUseCase(emojiRepository: EmojiRepository): EmojiUseCase = EmojiUseCase(emojiRepository)

    @ActivityRetainedScoped
    @Provides
    fun provideMainUseCase(emojiRepository: EmojiRepository,
                           userRepository: UserRepository,
                           @IoDispatcher ioDispatcher: CoroutineDispatcher):
            MainUseCase = MainUseCase(emojiRepository, userRepository, ioDispatcher)

    @ActivityRetainedScoped
    @Provides
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase = UserUseCase(userRepository)
}