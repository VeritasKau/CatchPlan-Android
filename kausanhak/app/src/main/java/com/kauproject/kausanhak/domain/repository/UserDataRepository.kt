package com.kauproject.kausanhak.domain.repository

import com.kauproject.kausanhak.domain.model.UserData
import kotlinx.coroutines.flow.StateFlow

interface UserDataRepository {
    suspend fun getUserData(): UserData
    suspend fun setUserData(key: String, value: String)

    fun getTokenData(): StateFlow<String>
}