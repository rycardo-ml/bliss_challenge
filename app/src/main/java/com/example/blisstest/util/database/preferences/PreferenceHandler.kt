package com.example.blisstest.util.database.preferences

import android.content.SharedPreferences
import java.util.*

private const val LAST_EMOJI_UPDATE = "LAST_EMOJI_UPDATE"
class PreferenceHandler(private val sharedPreferences: SharedPreferences) {

    fun updateLastEmojiRequest() {
        val newDate = Date()
        sharedPreferences
            .edit()
            .putLong(LAST_EMOJI_UPDATE, newDate.time)
            .apply()
    }

    fun shouldUpdateEmojis(): Boolean {
        val lastTimeInMillis = sharedPreferences.getLong(LAST_EMOJI_UPDATE, -1L)

        if (lastTimeInMillis == -1L) return true

        val milliToHour = 1000 * 60 * 60

        val difference = Date().time -lastTimeInMillis

        if (difference < 0) return true

        return (difference / milliToHour).toInt() >= 1
    }
}