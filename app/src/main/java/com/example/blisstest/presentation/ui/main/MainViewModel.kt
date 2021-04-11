package com.example.blisstest.presentation.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blisstest.util.model.Emoji
import com.example.blisstest.repository.EmojiRepository
import com.example.blisstest.repository.UserRepository
import com.example.blisstest.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel @ViewModelInject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _randomEmoji = MutableLiveData<Resource<Emoji>>()
    val randomEmoji: LiveData<Resource<Emoji>> get() = _randomEmoji

    fun fetchRandomEmoji() {
        Log.d(TAG, "init#fetchRandomEmoji")

        viewModelScope.launch {
            _randomEmoji.value = mainUseCase.getRandomEmoji()
        }
    }

    fun fetchUser(userName: String) {
        Log.d(TAG, "init fetchUser")

        viewModelScope.launch (Dispatchers.IO) {
            //val user = userRepository.getUser(userName)

            //Log.d(TAG, "user received ${user.name} - ${user.fetched}" )
        }

        Log.d(TAG, "end fetchUser")
    }
}