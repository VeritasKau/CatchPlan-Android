package com.kauproject.kausanhak.presentation.ui.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.EventRepository
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userDataRepository: UserDataRepository
): ViewModel() {
    private val _names = MutableStateFlow<String?>(null)
    var names = _names.asStateFlow()

    init {
        init()
    }

    private fun findEvent(eventId: Int): Event{
        return eventRepository.findEvent(eventId = eventId)
    }


    private fun init(){
        viewModelScope.launch {
            _names.value = userDataRepository.getUserData().name
        }
    }
}