package com.kauproject.kausanhak.presentation.ui.promotion.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.EventRepository
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userDataRepository: UserDataRepository
): ViewModel() {
    private val _favoriteList = MutableStateFlow<List<Event>>(emptyList())
    val favoriteList: StateFlow<List<Event>>
        get() = _favoriteList.asStateFlow()

    init {
        fetchEventList()
    }

    private fun fetchEventList(){
        viewModelScope.launch {
            val firstFavorite = userDataRepository.getUserData().firstFavorite
            val secondFavorite = userDataRepository.getUserData().secondFavorite
            val thirdFavorite = userDataRepository.getUserData().thirdFavorite
            val firstEventList = eventRepository.findEventCategory(firstFavorite)?.events ?: emptyList()
            val secondEventList = eventRepository.findEventCategory(secondFavorite)?.events ?: emptyList()
            val thirdEventList = eventRepository.findEventCategory(thirdFavorite)?.events ?: emptyList()
            val favoriteList = firstEventList + secondEventList + thirdEventList

            _favoriteList.value = favoriteList
        }
    }
}