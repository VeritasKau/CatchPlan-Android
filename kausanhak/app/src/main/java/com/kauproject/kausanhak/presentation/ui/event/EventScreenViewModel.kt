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

    private fun getEventCollection() {
        viewModelScope.launch {
            eventRepository.fetchEvents()

            _eventCollection.value = eventRepository.getEventCollection()
        }
    }
}