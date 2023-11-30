package com.kauproject.kausanhak.presentation.ui.recommend

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.remote.service.recommend.RecommendService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.EventRepository
import com.kauproject.kausanhak.domain.repository.PlaceEventRepository
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userDataRepository: UserDataRepository,
    private val recommendService: RecommendService,
    private val placeEventRepository: PlaceEventRepository
): ViewModel() {
    private val _names = MutableStateFlow<String?>(null)
    var names = _names.asStateFlow()
    private val _list = MutableStateFlow<MutableList<Event>>(mutableListOf())
    val list = _list.asStateFlow()

    init {
        fetchEvent()
    }

    private fun findEvent(eventId: Int): Event{
        return eventRepository.findEvent(eventId = eventId)
    }


    private fun init(){
        viewModelScope.launch {
            _names.value = userDataRepository.getUserData().name
        }
    }

    private fun fetchEvent(){
        viewModelScope.launch {
            placeEventRepository.fetchPlaceEvent(userDataRepository.getUserData().location).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> { _list.value = state.data.toMutableList() }
                    is State.ServerError -> {}
                    is State.Error -> {}
                }

            }

        }

    }

    private fun ex(){
        viewModelScope.launch {
            Log.d("TEST LOCATION", userDataRepository.getUserData().location)
            Log.d("TEST", "genre:${userDataRepository.getUserData().firstFavorite}")
            val response = recommendService.getRecommend(
                genre = "musical",
                mbti = userDataRepository.getUserData().mbti
            )
            val statusCode = response.code()

            if(statusCode == 200){
                Log.d("Success", "${response.body()?.combinedRecommendations?.firstOrNull()?.eventIds}")
            }else{
                Log.d("ERROR", "${response.errorBody()}")
            }

        }
    }
}