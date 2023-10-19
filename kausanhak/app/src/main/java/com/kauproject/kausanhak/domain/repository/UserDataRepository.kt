package com.kauproject.kausanhak.domain.repository

import com.kauproject.kausanhak.domain.model.Dummy
import com.kauproject.kausanhak.domain.model.UserData
import kotlinx.coroutines.flow.StateFlow

interface UserDataRepository {
    suspend fun getUserData(): Dummy
    suspend fun setUserData(key: String, value: String)

    fun getUserNumData(): StateFlow<String?>
    fun getTokenData(): StateFlow<String>
}