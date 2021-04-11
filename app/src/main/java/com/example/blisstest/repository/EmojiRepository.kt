package com.example.blisstest.repository

import android.util.Log
import androidx.room.withTransaction
import com.example.blisstest.util.database.dao.AppDatabase
import com.example.blisstest.util.network.GitHubService
import com.example.blisstest.util.database.dao.EmojiDao
import com.example.blisstest.util.model.Emoji
import com.example.blisstest.util.database.preferences.PreferenceHandler
import com.example.blisstest.util.di.hilt.IoDispatcher
import com.example.blisstest.util.network.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val TAG = "MainRepository"
class EmojiRepository(
    val apiService: GitHubService,
    val db: AppDatabase,
    val preferenceHandler: PreferenceHandler,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) {

    private val emojiDao = db.emojiDao()

    init {
        Log.d(TAG, "apiService $apiService")
        Log.d(TAG, "preferenceHandler $preferenceHandler")
        Log.d(TAG, "emojiDao $emojiDao")
    }

    fun getObservableEmojis() = networkBoundResource(
        query = {
            Log.d(TAG, "getObservableEmojis#query")
            emojiDao.getAllObservable()
        },
        fetch = {
            Log.d(TAG, "getObservableEmojis#fetch")
            //delay(5000)
            apiService.getEmojis()
        },
        shouldFetch = {
            Log.d(TAG, "getObservableEmojis#shouldFetch")
            it.isNullOrEmpty() || preferenceHandler.shouldUpdateEmojis()
        },
        saveFetchResult = {
            Log.d(TAG, "getObservableEmojis#saveFethResult")
            db.withTransaction {
                emojiDao.deleteAll()
                emojiDao.insert(*it.toTypedArray())
                preferenceHandler.updateLastEmojiRequest()
            }
        }
    )
}