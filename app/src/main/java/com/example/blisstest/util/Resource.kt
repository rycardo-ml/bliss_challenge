package com.example.blisstest.util

sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null,
    val fetchedFromApi: Boolean = false
){

    class Success<T>(data:T, fetchedFromApi: Boolean = false): Resource<T>(data, null, fetchedFromApi)
    class Loading<T>(data:T? = null): Resource<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null): Resource<T>(data, throwable)



}