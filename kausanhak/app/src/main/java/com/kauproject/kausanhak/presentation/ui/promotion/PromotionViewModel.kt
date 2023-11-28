package com.kauproject.kausanhak.presentation.ui.promotion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.remote.request.ScrapRequest
import com.kauproject.kausanhak.data.remote.service.promotion.GetPromotionService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.entitiy.EventDateEntity
import com.kauproject.kausanhak.domain.model.entitiy.ScrapEntity
import com.kauproject.kausanhak.domain.repository.EventDateRepository
import com.kauproject.kausanhak.domain.repository.EventRepository
import com.kauproject.kausanhak.domain.repository.PlaceEventRepository
import com.kauproject.kausanhak.domain.repository.PromotionRepository
import com.kauproject.kausanhak.domain.repository.ScrapRepository
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotionViewModel @Inject constructor(
    private val promotionRepository: PromotionRepository,
    private val userDataRepository: UserDataRepository,
    private val placeEventRepository: PlaceEventRepository,
    private val eventRepository: EventRepository,
): ViewModel() {
    private val _promotion = MutableStateFlow<List<Event>>(emptyList())
    val promotion: StateFlow<List<Event>>
        get() = _promotion.asStateFlow()

    private val _favorite = MutableStateFlow<List<Event>>(mutableListOf())
    val favorite: StateFlow<List<Event>>
        get() = _favorite.asStateFlow()

    private val _isPromotionError = MutableStateFlow<String>("")
    val isError: StateFlow<String>
        get() = _isPromotionError.asStateFlow()

    private val _placeEvent = MutableStateFlow<List<Event>>(emptyList())
    val placeEvent: StateFlow<List<Event>>
        get() = _placeEvent.asStateFlow()

    private val _isPlaceEventError = MutableStateFlow("")
    val isPlaceEventError: StateFlow<String>
        get() = _isPlaceEventError.asStateFlow()

    private val _isScrap = MutableStateFlow(false)
    val isScrap: StateFlow<Boolean>
        get() = _isScrap.asStateFlow()

    init {
        viewModelScope.launch {
            fetchEvent()
            fetchRecommendEvent()
        }
    }


    suspend fun fetchEvent(){
        val place = userDataRepository.getUserData().location
        promotionRepository.fetchPromotion().collect{ state->
            when(state){
                is State.Loading -> {}
                is State.Success -> { _promotion.value = state.data.reversed() }
                is State.ServerError -> { _isPromotionError.value = state.code.toString() }
                is State.Error -> { _isPromotionError.value = state.exception.toString() }
            }
        }

        if(place.isNotBlank() && place != ""){
            placeEventRepository.fetchPlaceEvent(place).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> { _placeEvent.value = state.data }
                    is State.ServerError -> { _isPlaceEventError.value = state.code.toString() }
                    is State.Error -> { _isPlaceEventError.value = state.exception.toString() }
                }
            }
        }
    }

    fun fetchRecommendEvent(){
        viewModelScope.launch {
            val firstFavorite = userDataRepository.getUserData().firstFavorite
            val secondFavorite = userDataRepository.getUserData().secondFavorite
            val thirdFavorite = userDataRepository.getUserData().thirdFavorite
            val firstEventList = eventRepository.findEventCategory(firstFavorite)?.events ?: emptyList()
            val secondEventList = eventRepository.findEventCategory(secondFavorite)?.events ?: emptyList()
            val thirdEventList = eventRepository.findEventCategory(thirdFavorite)?.events ?: emptyList()

            _favorite.value = (firstEventList.take(5)) + (secondEventList.take(5)) + (thirdEventList.take(5))
        }
    }



}