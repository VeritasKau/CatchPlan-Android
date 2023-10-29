package com.kauproject.kausanhak.domain.repository

import com.kauproject.kausanhak.data.model.EventDateEntity
import kotlinx.coroutines.flow.Flow


interface EventDateRepository {
    fun addEvent(event: EventDateEntity)
    fun deleteEvent(eventId: Int)
    fun readEvent(): Flow<List<EventDateEntity>>
}