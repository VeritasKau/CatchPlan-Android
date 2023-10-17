package com.kauproject.kausanhak.data.remote.repository

import android.content.Context
import com.kauproject.kausanhak.domain.model.UserData
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val context: Context
): UserDataRepository{
    override suspend fun getUserData(): UserData {
        TODO("Not yet implemented")
    }

    override suspend fun setUserData(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun getTokenData(): StateFlow<String> {
        TODO("Not yet implemented")
    }
}