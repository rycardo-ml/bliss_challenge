package com.example.blisstest.repository

import android.util.Log
import com.example.blisstest.util.network.GitHubService
import com.example.blisstest.util.database.dao.EmojiDao
import com.example.blisstest.util.model.Emoji
import com.example.blisstest.util.database.preferences.PreferenceHandler

private const val TAG = "MainRepository"
class MainRepository(
    private val apiService: GitHubService,
    private val emojiDao: EmojiDao,
    private val preferenceHandler: PreferenceHandler
) {

    init {
        Log.d(TAG, "apiService $apiService")
        Log.d(TAG, "preferenceHandler $preferenceHandler")
        Log.d(TAG, "emojiDao $emojiDao")
    }

    suspend fun getEmojis(): List<Emoji> {
        Log.d(TAG, "init getEmojis")

        if (emojiDao.getTableCount() == 0) {
            Log.d(TAG, "end getEmojis")
            return fetchEmojis()
        }

        if (preferenceHandler.shouldUpdateEmojis()) {
            Log.d(TAG, "fetch emojis in background")
            //TODO DO IN BACKGROUND
            fetchEmojis()
        }

        return emojiDao.getAll()
    }

    private suspend fun fetchEmojis(): List<Emoji> {
        Log.d(TAG, "init fetchEmojis")

        val emojis = apiService.getEmojis()
        registerEmojis(emojis)

        preferenceHandler.updateLastEmojiRequest()

        Log.d(TAG, "end fetchEmojis")
        return emojis
    }

    private fun registerEmojis(emojis: List<Emoji>) {
        Log.d(TAG, "init register emojis")

        emojiDao.insert(*emojis.toTypedArray())

        Log.d(TAG, "end register emojis")
    }
}