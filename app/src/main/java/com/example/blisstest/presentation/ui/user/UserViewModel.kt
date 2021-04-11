package com.example.blisstest.presentation.ui.user

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blisstest.presentation.ui.common.list_holder.ListHolder
import com.example.blisstest.util.model.User
import com.example.blisstest.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "UserViewModel"
class UserViewModel @ViewModelInject constructor(
    val repository: UserRepository
): ViewModel() {

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