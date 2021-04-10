package com.example.blisstest.util.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.blisstest.util.data.model.Emoji
import com.example.blisstest.util.data.model.User

@Database(entities = arrayOf(
    Emoji::class,
    User::class
)
    , version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun emojiDao(): EmojiDao

    abstract fun userDao(): UserDao

    companion object {

        @Volatile // All threads have immediate access to this property
        private var instance: AppDatabase? = null

        private val LOCK = Any() // Makes sure no threads making the same thing at the same time

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "blisstest.db"
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }
}