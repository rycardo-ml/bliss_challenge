package com.example.blisstest.util.preferences

import android.content.SharedPreferences
import java.util.*

private const val LAST_EMOJI_UPDATE = "LAST_EMOJI_UPDATE"
class PreferenceHandler(val sharedPreferences: SharedPreferences) {

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

        val MILLI_TO_HOUR = 1000 * 60 * 60

        val difference = Date().time -lastTimeInMillis

        if (difference < 0) return true

        return (difference / MILLI_TO_HOUR).toInt() >= 1
    }
}