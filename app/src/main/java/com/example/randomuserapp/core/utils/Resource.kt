package com.example.randomuserapp.core.utils

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(
        val message: String,
        val exception: Throwable? = null
    ) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}