package com.kauproject.kausanhak.presentation.ui.event

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.remote.service.event.GetEventService
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventCollection
import com.kauproject.kausanhak.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository
): ViewModel() {
    private val _eventCollection = MutableStateFlow<List<EventCollection>>(emptyList())
    val eventCollection: StateFlow<List<EventCollection>>
        get() = _eventCollection.asStateFlow()
    companion object{
        const val TAG = "EventScreenViewModel"
    }

    init {
        getEventCollection()
    }

    fun getEventCollection() {
        viewModelScope.launch {
            eventRepository.fetchEvents()

            val concert = EventCollection(
                id = 0,
                name = "콘서트",
                events = eventRepository.getConcert()
            )
            val exhibition = EventCollection(
                id = 1,
                name = "연극",
                events = eventRepository.getExhibition()
            )
            val musical = EventCollection(
                id = 2,
                name = "뮤지컬",
                events = eventRepository.getMusicals()
            )
            val drama = EventCollection(
                id = 3,
                name = "드라마",
                events = eventRepository.getDrama()
            )
            val camping = EventCollection(
                id = 4,
                name = "캠핑/레저",
                events = eventRepository.getCampings()
            )
            val korea = EventCollection(
                id = 5,
                name = "지역행사",
                events = eventRepository.getKorea()
            )
            val classic = EventCollection(
                id = 6,
                name = "클래식",
                events = eventRepository.getClassic()
            )
            val kids = EventCollection(
                id = 7,
                name = "아동/가족",
                events = eventRepository.getKids()
            )

            _eventCollection.value = listOf(
                concert, exhibition, musical, drama, camping, korea, classic, kids
            )
        }
    }

}