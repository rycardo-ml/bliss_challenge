package com.example.blisstest.util.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.blisstest.util.model.Emoji
import kotlinx.coroutines.flow.Flow



@Dao
interface EmojiDao {

    /*
    Flow is an observable that emmits a list of emojis every time the table changes
    Flow is similar to suspend function the value comes async
     */
    @Query("SELECT * FROM emoji")
    fun getAllObservable(): Flow<List<Emoji>>

    @Query("SELECT COUNT(*) FROM emoji")
    suspend fun getTableCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg emojis: Emoji)

    @Query("DELETE FROM emoji")
    suspend fun deleteAll()
}