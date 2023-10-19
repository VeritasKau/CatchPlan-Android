package com.kauproject.kausanhak.data.remote.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kauproject.kausanhak.domain.model.Dummy
import com.kauproject.kausanhak.domain.model.UserData
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val context: Context
): UserDataRepository{
    val Context.dataStore by preferencesDataStore(name = "user_data")
    private val _userNum = MutableStateFlow<String>("")

    companion object{
        private val USERNUM_KEY = stringPreferencesKey("userNum")

    }

    override suspend fun getUserData(): Dummy {
        val userData = context.dataStore.data
            .catch { exception ->
                if(exception is IOException){
                    emit(emptyPreferences())
                }else{
                    throw exception
                }
            }.map { preferences ->
                mapperToUserData(preferences = preferences)
            }.first()
        return userData
    }

    override suspend fun setUserData(key: String, value: String) {
        context.dataStore.edit { preferences->
            val preferencesKey = when(key){
                "userNum" -> {
                    _userNum.value = value
                    USERNUM_KEY
                }
                else -> throw IllegalArgumentException("Unknown key: $key")
            }
            preferences[preferencesKey] = value
        }
    }

    override fun getUserNumData(): StateFlow<String?> {
        return _userNum
    }

    override fun getTokenData(): StateFlow<String> {
        TODO("Not yet implemented")
    }

    private fun mapperToUserData(preferences: Preferences): Dummy{
        val userNum = preferences[USERNUM_KEY]

        return Dummy(userNum)
    }
}