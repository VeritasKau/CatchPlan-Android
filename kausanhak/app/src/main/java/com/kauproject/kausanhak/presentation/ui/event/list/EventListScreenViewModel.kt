package com.kauproject.kausanhak.presentation.ui.event.list

import androidx.lifecycle.ViewModel
import com.kauproject.kausanhak.domain.model.EventCollection
import com.kauproject.kausanhak.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventListScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository
): ViewModel() {
    fun findEventCollection(eventCollectionId: Int): EventCollection {
        return eventRepository.findEventCollection(eventCollectionId = eventCollectionId)
    }
}