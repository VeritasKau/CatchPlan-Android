package com.kauproject.kausanhak.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kauproject.kausanhak.domain.model.entitiy.EventDateEntity
import com.kauproject.kausanhak.domain.model.entitiy.ScrapEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScrapDAO {
    @Query("SELECT * FROM scrap_table")
    fun readScrap(): Flow<List<ScrapEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScrap(scrapEntity: ScrapEntity)

    @Query("DELETE FROM scrap_table WHERE eventId = :eventId")
    suspend fun deleteScrap(eventId: Int)

    @Query("SELECT * FROM scrap_table WHERE eventId = :eventId")
    suspend fun findEvent(eventId: Int): ScrapEntity?
}