package com.kauproject.kausanhak.data.remote.repository

import com.kauproject.kausanhak.data.db.EventDateDAO
import com.kauproject.kausanhak.data.model.EventDateEntity
import com.kauproject.kausanhak.domain.repository.EventDateRepository
import kotlinx.coroutines.flow.Flow

class EventDateRepositoryImpl(
    private val eventDateDao: EventDateDAO
): EventDateRepository {
    override suspend fun addEvent(event: EventDateEntity) {
        eventDateDao.addEventDate(event)
    }

    override suspend fun deleteEvent(eventId: Int) {
        eventDateDao.deleteEventDate(eventId)
    }

    override fun readEvent(): Flow<List<EventDateEntity>> {
        return eventDateDao.readEventDate()
    }

}