package com.example.blisstest.util.data.repository

import android.util.Log
import com.example.blisstest.util.data.api.GitHubService
import com.example.blisstest.util.data.dao.AppDatabase
import com.example.blisstest.util.data.dao.EmojiDao
import com.example.blisstest.util.data.dao.UserDao
import com.example.blisstest.util.data.model.User
import com.example.blisstest.util.di.DaggerAppComponent
import javax.inject.Inject

private const val TAG = "UserRepository"
class UserRepository {

    @Inject
    lateinit var apiService: GitHubService


    @Inject
    lateinit var database: AppDatabase
    val userDao: UserDao

    init {
        DaggerAppComponent.create().inject(this)

        userDao = database.userDao()

        Log.d(TAG, "apiService $apiService")
        Log.d(TAG, "database $database")
        Log.d(TAG, "userDao $userDao")
    }

    fun getUsers(): List<User> {
        Log.d(TAG, "init getUsers")

        val users = userDao.getAll()

        Log.d(TAG, "end getUsers")
        return users
    }

    suspend fun getUser(userName: String): User {
        Log.d(TAG, "init getUser")

        val user = userDao.getUser(userName) ?: fetchUser(userName)

        Log.d(TAG, "end getUser")

        return user
    }

    fun deleteUser(user: User) {
        Log.d(TAG, "init deleteUser")
        userDao.delete(user)
        Log.d(TAG, "end deleteUser")
    }

    private suspend fun fetchUser(userName: String): User {
        Log.d(TAG, "init fetchUser")

        val user = apiService.getUser(userName)

        userDao.insert(user)

        user.fetched = true

        Log.d(TAG, "end fetchUser")
        return user
    }

}