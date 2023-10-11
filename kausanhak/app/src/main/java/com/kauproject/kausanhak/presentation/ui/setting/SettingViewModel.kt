package com.kauproject.kausanhak.presentation.ui.setting

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingViewModel: ViewModel() {
    private val _userInfo = MutableStateFlow(UserInformation())
    val userInfo: StateFlow<UserInformation>
        get() = _userInfo.asStateFlow()

    private val _choiceMbti = MutableStateFlow(ChoiceMbti())
    val choiceMbti: StateFlow<ChoiceMbti>
        get() = _choiceMbti.asStateFlow()

    private val _userFavorite = MutableLiveData(UserFavorite())
    val userFavorite: LiveData<UserFavorite>
        get() = _userFavorite

    fun setUserName(name: String){
        _userInfo.update { it->
            it.copy(
                name = name
            )
        }
    }

    fun setUserGender(gender: String){
        _userInfo.update { it->
            it.copy(
                gender = gender
            )
        }
    }

    fun setUserMbti(mbti: String){
        _userInfo.update { it->
            it.copy(
                mbti = mbti
            )
        }
    }

    // 각 항목별로 클릭 데이터 업데이트
    fun setFirstMbti(data: String){
        _choiceMbti.update {
            it.copy(
                first = data
            )
        }
    }
    fun setSecondMbti(data: String){
        _choiceMbti.update {
            it.copy(
                second = data
            )
        }
    }
    fun setThirdMbti(data: String){
        _choiceMbti.update {
            it.copy(
                third = data
            )
        }
    }
    fun setLastMbti(data: String){
        _choiceMbti.update {
            it.copy(
                last = data
            )
        }
    }

    fun setUserFavorite(favoriteList: List<String?>){
        _userInfo.update {
            it.copy(
                firstFavorite = favoriteList[0],
                secondFavorite = favoriteList[1],
                thirdFavorite = favoriteList[2]
            )
        }
    }
}

data class UserInformation(
    val name: String = "",
    val gender: String = "",
    val mbti: String = "",
    var firstFavorite: String? = null,
    var secondFavorite: String? = null,
    var thirdFavorite: String? = null,
)

data class UserFavorite(
    var firstFavorite: String? = null,
    var secondFavorite: String? = null,
    var thirdFavorite: String? = null,
)

data class ChoiceMbti(
    val first: String = "",
    val second: String = "",
    val third: String = "",
    val last: String = ""
)
