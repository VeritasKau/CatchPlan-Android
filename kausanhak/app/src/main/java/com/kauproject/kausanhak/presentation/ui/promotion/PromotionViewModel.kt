package com.kauproject.kausanhak.presentation.ui.promotion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.remote.service.promotion.GetPromotionService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.PlaceEventRepository
import com.kauproject.kausanhak.domain.repository.PromotionRepository
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotionViewModel @Inject constructor(
    private val promotionRepository: PromotionRepository,
    private val userDataRepository: UserDataRepository,
    private val placeEventRepository: PlaceEventRepository
): ViewModel() {
    private val _userNick = MutableStateFlow<String?>(null)
    val userNick: StateFlow<String?>
        get() = _userNick.asStateFlow()

    private val _promotion = MutableStateFlow<List<Event>>(emptyList())
    val promotion: StateFlow<List<Event>>
        get() = _promotion.asStateFlow()

    private val _isPromotionError = MutableStateFlow<String>("")
    val isError: StateFlow<String>
        get() = _isPromotionError.asStateFlow()

    private val _placeEvent = MutableStateFlow<List<Event>>(emptyList())
    val placeEvent: StateFlow<List<Event>>
        get() = _placeEvent.asStateFlow()

    private val _isPlaceEventError = MutableStateFlow("")
    val isPlaceEventError: StateFlow<String>
        get() = _isPlaceEventError.asStateFlow()

    init {
        viewModelScope.launch {
            fetchEvent()
            _userNick.value = userDataRepository.getUserData().name
        }
    }


    private suspend fun fetchEvent(){
        val place = userDataRepository.getUserData().location
        promotionRepository.fetchPromotion().collect{ state->
            when(state){
                is State.Loading -> {}
                is State.Success -> { _promotion.value = state.data }
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

}