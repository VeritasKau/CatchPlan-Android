package com.kauproject.kausanhak.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kauproject.kausanhak.data.model.EventDateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface EventDateDAO {
    @Query("SELECT * FROM event_calendar_table")
    fun readEventDate(): Flow<List<EventDateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEventDate(eventDateEntity: EventDateEntity)

    @Query("DELETE FROM event_calendar_table WHERE eventId = :eventId")
    fun deleteEventDate(eventId: Int)

}