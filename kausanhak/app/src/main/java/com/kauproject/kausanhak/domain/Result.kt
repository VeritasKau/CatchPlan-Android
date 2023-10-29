package com.kauproject.kausanhak.domain

import java.io.IOException

sealed class Result<out T>{
    object Uninitialized: Result<Nothing>()

    object Loading: Result<Nothing>()

    object Empty: Result<Nothing>()

    data class Success<T>(val data: T): Result<T>()

    data class Error(val exception: Throwable): Result<Nothing>(){
        val isError = exception is IOException
    }
}
