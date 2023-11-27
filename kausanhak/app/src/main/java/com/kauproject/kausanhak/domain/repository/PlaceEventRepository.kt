package com.kauproject.kausanhak.domain.repository
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface PlaceEventRepository {
    fun fetchPlaceEvent(place: String): Flow<State<List<Event>>>
}