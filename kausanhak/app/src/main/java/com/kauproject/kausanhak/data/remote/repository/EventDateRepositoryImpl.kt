package com.kauproject.kausanhak.data.remote.repository

import android.util.Log
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

    override fun getAllEvent(): Flow<Result<List<EventDateEntity>>> = flow {
        emit(Result.Loading)
        eventDateDao.readEventDate().collect{
            if(it.isEmpty()){
                Log.d("EventDateRepo", "isEmpty")
                emit(Result.Empty)
            }else{
                Log.d("EventDateRepo", "isSuccess")
                emit(Result.Success(it))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
        Log.d("EventDateRepo", "ERROR")
    }

}