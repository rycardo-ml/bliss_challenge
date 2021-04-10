package com.example.blisstest.list.emoji

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blisstest.util.ListHolder
import com.example.blisstest.util.data.model.User
import com.example.blisstest.util.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "UserViewModel"
class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    private val holder = ListHolder<User>()
    private val ldUsers = MutableLiveData<ListHolder<User>>()

    fun fetchUsers() {
        Log.d(TAG, "init fetchUsers")

        viewModelScope.launch (Dispatchers.IO) {
            holder.resetList(repository.getUsers())
            ldUsers.postValue(holder)
        }

        Log.d(TAG, "end fetchUsers")
    }

    fun deleteUser(position: Int) {
        viewModelScope.launch (Dispatchers.IO) {
            holder.clearChangedItems()

            val user = holder.get(position)

            repository.deleteUser(user)

            holder.removeItem(position)
            ldUsers.postValue(holder)
        }
    }

    fun getUsers(): LiveData<ListHolder<User>> = ldUsers
}