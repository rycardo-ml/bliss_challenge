package com.example.blisstest.presentation.ui.google.pagination

//TODO search for the sealed word

sealed class PaginationStatus {
    object Loading : PaginationStatus()
    object Empty : PaginationStatus()
    object NotEmpty : PaginationStatus()
    data class Error(val error: String): PaginationStatus()
}