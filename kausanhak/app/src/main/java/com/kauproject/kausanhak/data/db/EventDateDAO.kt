package com.kauproject.kausanhak.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kauproject.kausanhak.domain.model.entitiy.EventDateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDateDAO {
    @Query("SELECT * FROM event_calendar_table")
    fun readEventDate(): Flow<List<EventDateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEventDate(eventDateEntity: EventDateEntity)

    @Query("DELETE FROM event_calendar_table WHERE eventId = :eventId")
    suspend fun deleteEventDate(eventId: Int)

}