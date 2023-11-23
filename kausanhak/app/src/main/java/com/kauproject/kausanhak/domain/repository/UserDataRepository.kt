package com.kauproject.kausanhak.domain.repository

import androidx.lifecycle.LiveData
import com.kauproject.kausanhak.domain.model.UserData

interface UserDataRepository {
    suspend fun getUserData(): UserData
    suspend fun setUserData(key: String, value: String)
    fun getTokenData(): LiveData<String?>
}