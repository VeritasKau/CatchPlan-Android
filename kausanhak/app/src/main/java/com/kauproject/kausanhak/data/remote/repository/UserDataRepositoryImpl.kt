package com.kauproject.kausanhak.data.remote.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kauproject.kausanhak.domain.model.Dummy
import com.kauproject.kausanhak.domain.model.UserData
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val context: Context
): UserDataRepository{
    val Context.dataStore by preferencesDataStore(name = "user_data")
    private val _userData = MutableStateFlow<UserData>(UserData())
    private val _tokenLiveData = MutableLiveData<String>()

    companion object{
        private val USERNUM_KEY = stringPreferencesKey("userNum")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val NAME_KEY = stringPreferencesKey("name")
        private val PLATFORM_KEY = stringPreferencesKey("platform")
        private val GENDER_KEY = stringPreferencesKey("gender")
        private val MBTI_KEY = stringPreferencesKey("mbti")
        private val FAV_FIRST_KEY = stringPreferencesKey("first")
        private val FAV_SEC_KEY = stringPreferencesKey("second")
        private val FAV_THR_KEY = stringPreferencesKey("third")

    }


    override suspend fun getUserData(): UserData {
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
                    _userData.value.num = value
                    USERNUM_KEY
                }
                "token" -> {
                    _tokenLiveData.postValue(value)
                    _userData.value.token = value
                    TOKEN_KEY
                }
                "name" -> {
                    _userData.value.name = value
                    NAME_KEY
                }
                "platform" -> {
                    _userData.value.platform = value
                    PLATFORM_KEY
                }
                "gender" -> {
                    _userData.value.gender = value
                    GENDER_KEY
                }
                "mbti" -> {
                    _userData.value.mbti = value
                    MBTI_KEY
                }
                "first" -> {
                    _userData.value.firstFavorite = value
                    FAV_FIRST_KEY
                }
                "second" -> {
                    _userData.value.secondFavorite = value
                    FAV_SEC_KEY
                }
                "third" -> {
                    _userData.value.thirdFavorite = value
                    FAV_THR_KEY
                }
                else -> throw IllegalArgumentException("Unknown key: $key")
            }
            preferences[preferencesKey] = value
        }
    }

    override fun getTokenData(): LiveData<String?> {
        return _tokenLiveData
    }

    private fun mapperToUserData(preferences: Preferences): UserData{
        val userNum = preferences[USERNUM_KEY] ?: ""
        val name = preferences[NAME_KEY] ?: ""
        val platform = preferences[PLATFORM_KEY] ?: ""
        val token = preferences[TOKEN_KEY] ?: ""
        val gender = preferences[GENDER_KEY] ?: ""
        val mbti = preferences[MBTI_KEY] ?: ""
        val first = preferences[FAV_FIRST_KEY] ?: ""
        val second = preferences[FAV_SEC_KEY] ?: ""
        val third = preferences[FAV_THR_KEY] ?: ""

        return UserData(name, platform, token, userNum, gender, mbti, first, second, third)
    }
}