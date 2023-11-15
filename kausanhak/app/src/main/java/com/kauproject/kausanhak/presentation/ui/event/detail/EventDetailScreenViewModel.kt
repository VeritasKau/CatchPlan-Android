package com.kauproject.kausanhak.presentation.ui.event.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.remote.request.ScrapRequest
import com.kauproject.kausanhak.data.remote.service.scrap.ScrapSignService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.entitiy.EventDateEntity
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.entitiy.ScrapEntity
import com.kauproject.kausanhak.domain.repository.EventDateRepository
import com.kauproject.kausanhak.domain.repository.EventRepository
import com.kauproject.kausanhak.domain.repository.ScrapRepository
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EventDetailScreenViewModel @Inject constructor(
    private val eventDateRepository: EventDateRepository,
    private val eventRepository: EventRepository,
    private val scrapRepository: ScrapRepository,
    private val scrapSignService: ScrapSignService,
    private val userDataRepository: UserDataRepository
): ViewModel() {
    private val _isScrap = MutableStateFlow(false)
    val isScrap = _isScrap.asStateFlow()

    fun addEventDate(eventId: Int, date: String, name: String, place: String, image: String){
        viewModelScope.launch(Dispatchers.IO){
            eventDateRepository.addEvent(
                EventDateEntity(
                    eventId = eventId,
                    date = date,
                    name = name,
                    place = place,
                    image = image
                )
            )
        }
    }

    fun addScrap(eventId: Int, date: String, name: String, place: String, image: String): Flow<State<Int>> = flow {
        emit(State.Loading)

        val request = ScrapRequest(
            eventId = eventId,
            uniqueUserInfo = userDataRepository.getUserData().num
        )
        val response = scrapSignService.scrapSign(request)
        val statusCode = response.code()

        if(statusCode == 200){
            scrapRepository.addScrap(
                ScrapEntity(
                    eventId = eventId,
                    date = date,
                    name = name,
                    place = place,
                    image = image
                )
            )
            _isScrap.value = true
            emit(State.Success(statusCode))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    fun deleteScrap(eventId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            scrapRepository.deleteScrap(eventId)
            _isScrap.value = false
        }
    }

    fun findScrap(eventId: Int){
        viewModelScope.launch {
            _isScrap.value = scrapRepository.findScrap(eventId) != null
        }
    }

    fun findEvent(eventId: Int): Event{
        return eventRepository.findEvent(eventId = eventId)
    }

}