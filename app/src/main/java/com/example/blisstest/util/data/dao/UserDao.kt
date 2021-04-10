package com.example.blisstest.util.data.dao

import androidx.room.*
import com.example.blisstest.util.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE user_name = :userName")
    fun getUser(userName: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)
}