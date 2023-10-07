package com.kauproject.kausanhak.presentation.ui.setting

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingViewModel: ViewModel() {
    private val _userMbti = MutableStateFlow(UserMbti())
    val userMbti: StateFlow<UserMbti>
        get() = _userMbti.asStateFlow()

    private val _userFavorite = MutableStateFlow(UserFavorite())
    val userFavorite: StateFlow<UserFavorite>
        get() = _userFavorite.asStateFlow()

    fun setUserMbti(mbti: String){
        _userMbti.update { it->
            it.copy(
                mbti = mbti
            )
        }
    }

    fun setFavorite(favoriteList: List<String>){
        _userFavorite.update {
            it.copy(
                first = favoriteList[0],
                second = favoriteList[1],
                third = favoriteList[2]
            )
        }
    }
}
data class UserMbti(
    val mbti: String = ""
)


data class UserFavorite(
    val first: String = "",
    val second: String = "",
    val third: String = ""
)