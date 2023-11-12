package com.kauproject.kausanhak.data.remote.repository

import com.kauproject.kausanhak.data.db.ScrapDAO
import com.kauproject.kausanhak.domain.model.entitiy.ScrapEntity
import com.kauproject.kausanhak.domain.repository.ScrapRepository
import kotlinx.coroutines.flow.Flow

class ScrapRepositoryImpl(
    private val scrapDAO: ScrapDAO
): ScrapRepository {
    override suspend fun addScrap(scrapEntity: ScrapEntity) {
        scrapDAO.addScrap(scrapEntity)
    }

    override suspend fun deleteScrap(eventId: Int) {
        scrapDAO.deleteScrap(eventId)
    }

    override suspend fun findScrap(eventId: Int): ScrapEntity? {
        return scrapDAO.findEvent(eventId)
    }

    override fun readScrap(): Flow<List<ScrapEntity>> {
        return scrapDAO.readScrap()
    }
}