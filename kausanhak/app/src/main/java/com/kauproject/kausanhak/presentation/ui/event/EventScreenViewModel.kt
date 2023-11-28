package com.kauproject.kausanhak.presentation.ui.event

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.remote.service.event.GetEventService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventCollection
import com.kauproject.kausanhak.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository
): ViewModel() {
    private val _collection = MutableStateFlow<List<EventCollection>>(emptyList())
    var collection = _collection.asStateFlow()
    private val _isError = MutableStateFlow(false)
    var isError = _isError.asStateFlow()

    companion object{
        const val TAG = "EventScreenViewModel"
    }

    init {
        viewModelScope.launch {
            getEventCollection()
        }
    }

    fun getEventCollection() {
        viewModelScope.launch {
            eventRepository.fetchEvents().collect{ state->
                when(state){
                    is State.Loading -> {  }
                    is State.Success -> { _collection.value = state.data }
                    is State.ServerError -> { _isError.value = true }
                    is State.Error -> { _isError.value = true }
                }
            }
        }
    }
}