package com.example.blisstest.presentation.ui.emoji

import com.example.blisstest.repository.EmojiRepository
import com.example.blisstest.util.Resource
import com.example.blisstest.util.model.Emoji
import kotlinx.coroutines.flow.Flow

class EmojiUseCase(private val emojiRepository: EmojiRepository) {

    fun getEmojis(): Flow<Resource<List<Emoji>>> {
        return emojiRepository.getObservableEmojis()
    }


}