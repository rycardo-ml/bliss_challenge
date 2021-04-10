package com.example.blisstest.main.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blisstest.util.data.model.Emoji
import com.example.blisstest.util.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"
class MainViewModel() : ViewModel() {

    private val repository = MainRepository()

    private val lvRandomEmoji = MutableLiveData<Emoji>()

    fun fetchRandomEmoji() {
        Log.d(TAG, "init fetchRandomEmoji")

        viewModelScope.launch (Dispatchers.IO) {
            val result = repository.getEmojis()
            val random = (Math.random() * result.size).toInt()

            lvRandomEmoji.postValue(result[random])
        }

        Log.d(TAG, "end fetchRandomEmoji")
    }

    fun getRandomEmoji(): LiveData<Emoji> = lvRandomEmoji
}