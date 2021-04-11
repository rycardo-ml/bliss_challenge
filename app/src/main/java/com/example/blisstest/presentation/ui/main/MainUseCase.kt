package com.example.blisstest.presentation.ui.main

import com.example.blisstest.repository.EmojiRepository
import com.example.blisstest.repository.UserRepository
import com.example.blisstest.util.Resource
import com.example.blisstest.util.di.hilt.IoDispatcher
import com.example.blisstest.util.model.Emoji
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class MainUseCase(
    private val emojiRepository: EmojiRepository,
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getRandomEmoji(): Resource<Emoji> {
        return withContext(ioDispatcher) {
            val item = emojiRepository.getObservableEmojis().first()

            if (item.data.isNullOrEmpty())
                return@withContext Resource.Error(Throwable("failed to find item"))

            val random = (Math.random() * item.data.size).toInt()
            return@withContext Resource.Success(item.data[random])
        }
    }
}