package com.kauproject.kausanhak.presentation.ui.event.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.domain.model.entitiy.EventDateEntity
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.entitiy.ScrapEntity
import com.kauproject.kausanhak.domain.repository.EventDateRepository
import com.kauproject.kausanhak.domain.repository.EventRepository
import com.kauproject.kausanhak.domain.repository.ScrapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EventDetailScreenViewModel @Inject constructor(
    private val eventDateRepository: EventDateRepository,
    private val eventRepository: EventRepository,
    private val scrapRepository: ScrapRepository
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

    fun addScrap(eventId: Int, date: String, name: String, place: String, image: String){
        viewModelScope.launch(Dispatchers.IO) {
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
        }
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