package com.kauproject.kausanhak.domain.repository

import com.kauproject.kausanhak.data.remote.response.GetPlaceResponse
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventCollection
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun fetchEvents(): Flow<State<List<EventCollection>>>
    fun findEvent(eventId: Int): Event
    fun findEventCollection(eventCollectionId: Int): EventCollection
    fun findEventCategory(category: String): EventCollection?
}