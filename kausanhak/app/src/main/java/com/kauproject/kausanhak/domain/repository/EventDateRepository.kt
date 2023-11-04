package com.kauproject.kausanhak.domain.repository

import com.kauproject.kausanhak.data.model.EventDateEntity
import kotlinx.coroutines.flow.Flow


interface EventDateRepository {
    suspend fun addEvent(event: EventDateEntity)
    suspend fun deleteEvent(eventId: Int)
    fun readEvent(): Flow<List<EventDateEntity>>
}