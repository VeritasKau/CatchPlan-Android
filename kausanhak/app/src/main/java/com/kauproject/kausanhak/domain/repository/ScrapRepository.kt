package com.kauproject.kausanhak.domain.repository

import com.kauproject.kausanhak.domain.model.entitiy.ScrapEntity
import kotlinx.coroutines.flow.Flow

interface ScrapRepository {
    suspend fun addScrap(scrapEntity: ScrapEntity)
    suspend fun deleteScrap(eventId: Int)
    suspend fun findScrap(eventId: Int): ScrapEntity?
    fun readScrap(): Flow<List<ScrapEntity>>
}