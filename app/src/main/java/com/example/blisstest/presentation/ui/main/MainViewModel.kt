package com.example.blisstest.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blisstest.util.model.Emoji
import com.example.blisstest.util.Resource
import com.example.blisstest.util.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    val mainUseCase: MainUseCase
) : ViewModel() {

    private val _randomEmoji = MutableLiveData<Resource<Emoji>>()
    val randomEmoji: LiveData<Resource<Emoji>> get() = _randomEmoji

    private val _fetchedUser = MutableLiveData<Resource<User?>>()
    val fetchedUser: LiveData<Resource<User?>> get() = _fetchedUser

    fun fetchRandomEmoji() {
        Log.d(TAG, "init#fetchRandomEmoji")

        viewModelScope.launch {
            _randomEmoji.value = mainUseCase.getRandomEmoji()
        }
    }

    fun fetchUser(userName: String) {
        Log.d(TAG, "init#fetchUser")

        viewModelScope.launch {
            val item = mainUseCase.fetchUser(userName)
            _fetchedUser.value = item
        }
    }
}