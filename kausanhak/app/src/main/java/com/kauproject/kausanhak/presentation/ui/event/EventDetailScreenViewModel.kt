package com.kauproject.kausanhak.presentation.ui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.model.EventDateEntity
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.EventDateRepository
import com.kauproject.kausanhak.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailScreenViewModel @Inject constructor(
    private val eventDateRepository: EventDateRepository,
    private val eventRepository: EventRepository
): ViewModel() {

    fun addEventDate(eventId: Int, date: String, name: String, place: String, image: String){
        viewModelScope.launch(Dispatchers.IO){
            eventDateRepository.addEvent(
                EventDateEntity(
                    eventId = eventId,
                    date = date,
                    name = name,
                    place = place,
                    image = image
                ))
        }
    }

    fun findEvent(eventId: Int): Event{
        return eventRepository.findEvent(eventId = eventId)
    }

}