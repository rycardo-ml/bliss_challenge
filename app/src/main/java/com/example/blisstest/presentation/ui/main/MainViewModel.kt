package com.example.blisstest.presentation.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blisstest.util.model.Emoji
import com.example.blisstest.repository.MainRepository
import com.example.blisstest.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"


class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val lvRandomEmoji = MutableLiveData<Emoji>()

    fun fetchRandomEmoji() {
        Log.d(TAG, "init fetchRandomEmoji")

        viewModelScope.launch (Dispatchers.IO) {
            val result = mainRepository.getEmojis()
            val random = (Math.random() * result.size).toInt()

            lvRandomEmoji.postValue(result[random])
        }

        Log.d(TAG, "end fetchRandomEmoji")
    }

    fun fetchUser(userName: String) {
        Log.d(TAG, "init fetchUser")

        viewModelScope.launch (Dispatchers.IO) {
            val user = userRepository.getUser(userName)

            Log.d(TAG, "user received ${user.name} - ${user.fetched}" )
        }

        Log.d(TAG, "end fetchUser")
    }

    fun getRandomEmoji(): LiveData<Emoji> = lvRandomEmoji
}