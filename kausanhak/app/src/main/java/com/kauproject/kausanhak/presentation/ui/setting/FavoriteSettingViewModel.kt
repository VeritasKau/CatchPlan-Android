package com.kauproject.kausanhak.presentation.ui.setting

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FavoriteSettingViewModel: ViewModel() {
    private val _userFavorite = MutableStateFlow(UserFavorite())
    val userFavorite: StateFlow<UserFavorite>
        get() = _userFavorite.asStateFlow()

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