package com.example.blisstest.repository

import android.util.Log
import com.example.blisstest.util.network.GitHubService
import com.example.blisstest.util.database.dao.UserDao
import com.example.blisstest.util.model.User

private const val TAG = "UserRepository"
class UserRepository(
    private val apiService: GitHubService,
    private val userDao: UserDao
) {

    init {
        Log.d(TAG, "apiService $apiService")
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