package com.example.blisstest.presentation.ui.emoji

import android.util.Log
import androidx.lifecycle.*
import com.example.blisstest.util.model.Emoji
import com.example.blisstest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "UserViewModel"

@HiltViewModel
class EmojiViewModel @Inject constructor(
    private val emojiUseCase: EmojiUseCase
) : ViewModel() {


    private val _emojis = MutableLiveData<Resource<List<Emoji>>>()
    val emojis: LiveData<Resource<List<Emoji>>> get() = _emojis

    fun fetchEmojis() {
        Log.d(TAG, "fetchEmojis")

        viewModelScope.launch {
            val flowEmojis = emojiUseCase.getEmojis()
            (emojis as MutableLiveData).value = flowEmojis.first()
        }
    }
}