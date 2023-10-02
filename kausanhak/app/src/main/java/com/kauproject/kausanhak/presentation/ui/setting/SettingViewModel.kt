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

    fun setUserMbti(mbti: String){
        _userMbti.update { it->
            it.copy(
                mbti = mbti
            )
        }
    }
}