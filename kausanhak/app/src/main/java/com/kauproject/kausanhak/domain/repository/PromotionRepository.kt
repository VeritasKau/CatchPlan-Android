package com.kauproject.kausanhak.domain.repository

import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface PromotionRepository {
    fun fetchPromotion(): Flow<State<List<Event>>>
    fun findPromotion(id: Int): Event
    fun getPromotion(): List<Event>
}