package com.example.blisstest.presentation.ui.emoji

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blisstest.util.model.Emoji
import com.example.blisstest.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "UserViewModel"
class EmojiViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val ldEmojis = MutableLiveData<List<Emoji>>()

    fun fetchEmojis() {
        Log.d(TAG, "init fetchEmojis")

        viewModelScope.launch (Dispatchers.IO) {
            val result = mainRepository.getEmojis()
            ldEmojis.postValue(result)
        }

        Log.d(TAG, "end fetchEmojis")
    }

    fun getEmojis(): LiveData<List<Emoji>> = ldEmojis
}