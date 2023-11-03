package com.kauproject.kausanhak.domain.repository

import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventCollection

interface EventRepository {
    suspend fun fetchEvents()
    fun findEvent(eventId: Int): Event
    fun getEventCollection(): List<EventCollection>
    fun findEventCollection(eventCollectionId: Int): EventCollection
}