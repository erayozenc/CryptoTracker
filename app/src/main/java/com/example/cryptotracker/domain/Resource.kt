package com.example.cryptotracker.domain

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    object Loading: Resource<Nothing>()
    data class Error<out T>(val message: String, val data: T? = null) : Resource<T>()
}

