package com.example.blisstest.repository

import android.util.Log
import androidx.room.withTransaction
import com.example.blisstest.util.database.dao.AppDatabase
import com.example.blisstest.util.network.GitHubService
import com.example.blisstest.util.database.dao.UserDao
import com.example.blisstest.util.model.User
import com.example.blisstest.util.network.networkBoundResource
import kotlinx.coroutines.delay

private const val TAG = "UserRepository"
class UserRepository(
    val apiService: GitHubService,
    val db: AppDatabase
) {

    private val userDao = db.userDao()

    init {
        Log.d(TAG, "apiService $apiService")
        Log.d(TAG, "userDao $userDao")
    }

    suspend fun getUsers(): List<User> {
        Log.d(TAG, "init getUsers")

        val users = userDao.getAll()

        Log.d(TAG, "end getUsers")
        return users
    }

    suspend fun deleteUser(user: User) {
        Log.d(TAG, "init deleteUser")
        userDao.delete(user)
        Log.d(TAG, "end deleteUser")
    }

    fun getOrFetchUser(userName: String) = networkBoundResource(
        query = {
            Log.d(TAG, "getOrFetchUser#query $userName")
            userDao.getUser(userName)
        },
        fetch = {
            Log.d(TAG, "getOrFetchUser#fetch")
            //delay(5000)
            apiService.getUser(userName)
        },
        shouldFetch = {
            Log.d(TAG, "getOrFetchUser#shouldFetch")
            shouldFetch(it)
        },
        saveFetchResult = {
            Log.d(TAG, "getOrFetchUser#saveFethResult")
            db.withTransaction {
                userDao.insert(it)
            }
        }
    )

    private fun shouldFetch(user: User?): Boolean {
        user ?: return true
        return false
    }
}