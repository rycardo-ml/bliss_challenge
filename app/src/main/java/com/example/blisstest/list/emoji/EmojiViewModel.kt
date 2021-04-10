package com.example.blisstest.list.emoji

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blisstest.util.data.model.Emoji
import com.example.blisstest.util.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "UserViewModel"
class EmojiViewModel : ViewModel() {

    private val repository = MainRepository()

    private val ldEmojis = MutableLiveData<List<Emoji>>()

    fun fetchEmojis() {
        Log.d(TAG, "init fetchEmojis")

        viewModelScope.launch (Dispatchers.IO) {
            val result = repository.getEmojis()
            ldEmojis.postValue(result)
        }

        Log.d(TAG, "end fetchEmojis")
    }

    fun getEmojis(): LiveData<List<Emoji>> = ldEmojis
}