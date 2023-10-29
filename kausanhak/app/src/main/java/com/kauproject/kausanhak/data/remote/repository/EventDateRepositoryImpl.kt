package com.kauproject.kausanhak.data.remote.repository

import com.kauproject.kausanhak.data.db.EventDateDAO
import com.kauproject.kausanhak.data.model.EventDateEntity
import com.kauproject.kausanhak.domain.Result
import com.kauproject.kausanhak.domain.repository.EventDateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class EventDateRepositoryImpl(
    private val eventDateDao: EventDateDAO
): EventDateRepository {
    override fun addEvent(event: EventDateEntity) {
        eventDateDao.addEventDate(event)
    }

    override fun deleteEvent(eventId: Int) {
        eventDateDao.deleteEventDate(eventId)
    }

    override fun readEvent(): Flow<List<EventDateEntity>>{
        return eventDateDao.readEventDate()
    }

    fun getAllEvent() = flow {
        emit(Result.Loading)
        eventDateDao.readEventDate().collect{
            if(it.isEmpty()){
                emit(Result.Empty)
            }else{
                emit(Result.Success(it))
            }
        }
    }.catch { e -> emit(Result.Error(e)) }

}