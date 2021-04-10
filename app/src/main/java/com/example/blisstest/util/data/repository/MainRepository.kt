package com.example.blisstest.util.data.repository

import android.util.Log
import com.example.blisstest.util.data.api.GitHubService
import com.example.blisstest.util.data.dao.AppDatabase
import com.example.blisstest.util.data.dao.EmojiDao
import com.example.blisstest.util.data.model.Emoji
import com.example.blisstest.util.di.DaggerAppComponent
import com.example.blisstest.util.preferences.PreferenceHandler
import javax.inject.Inject

private const val TAG = "MainRepository"
class MainRepository {

    @Inject
    lateinit var apiService: GitHubService

    @Inject
    lateinit var database: AppDatabase
    lateinit var emojiDao: EmojiDao

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    init {
        DaggerAppComponent.create().inject(this)

        emojiDao = database.emojiDao()

        Log.d(TAG, "apiService $apiService")
        Log.d(TAG, "database $database")
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

    suspend fun fetchEmojis(): List<Emoji> {
        Log.d(TAG, "init fetchEmojis")

        val emojis = apiService.getEmojis()
        registerEmojis(emojis)

        preferenceHandler.updateLastEmojiRequest()

        Log.d(TAG, "end fetchEmojis")
        return emojis
    }

    suspend fun registerEmojis(emojis: List<Emoji>) {
        Log.d(TAG, "init register emojis")

        val dao = database.emojiDao()
        dao.insert(*emojis.toTypedArray())

        Log.d(TAG, "end register emojis")
    }
}