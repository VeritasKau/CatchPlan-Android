package com.kauproject.kausanhak.presentation.ui.recommend

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.remote.request.RecommendRequest
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

    private val _recommendList = MutableStateFlow<MutableList<Event>>(mutableListOf())
    val recommendList = _recommendList.asStateFlow()

    init {
        fetchRecommendEvent()
    }

    private fun findEvent(eventId: Int): Event? {
        return eventRepository.findEvent(eventId = eventId)
    }


    private fun fetchRecommendEvent(){
        viewModelScope.launch {
            val tmpList = mutableListOf<Event>()
            val request = RecommendRequest(
                uniqueUserInfo = userDataRepository.getUserData().num
            )
            val response = recommendService.getRecommend(request)
            val statusCode = response.code()

            if(statusCode == 200){
                response.body()?.recommendedEventId?.forEach { eventId->
                    eventId?.let { id ->
                        eventRepository.findEvent(id) }?.let{ event ->
                        tmpList.add(event) }
                }
                _recommendList.value = tmpList
            }else{
                Log.d("ERROR", "${response.errorBody()}")
            }

        }
    }
}