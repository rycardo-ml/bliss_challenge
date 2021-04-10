package com.example.blisstest.util.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.blisstest.util.data.model.Emoji

@Dao
interface EmojiDao {

    @Query("SELECT * FROM emoji")
    fun getAll(): List<Emoji>

    @Query("SELECT * FROM emoji LIMIT 1")
    fun getRandomEmoji(): Emoji

    @Query("SELECT COUNT(*) FROM emoji")
    fun getTableCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg emojis: Emoji)
}