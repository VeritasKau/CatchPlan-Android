package com.kauproject.kausanhak.data.remote.repository

import com.kauproject.kausanhak.data.remote.service.event.GetPlaceEventService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.PlaceEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class PlaceEventRepositoryImpl(
    private val getPlaceEventService: GetPlaceEventService
): PlaceEventRepository {
    private var placeEventList = emptyList<Event>()
    override fun fetchPlaceEvent(place: String): Flow<State<List<Event>>> = flow {
        emit(State.Loading)

        val response = getPlaceEventService.getPlaceEvent(place)
        val statusCode = response.code()

        if(statusCode == 200){
            placeEventList = response.body()?.map {
                Event(
                    id = it.id ?: -1,
                    name = it.text ?: "",
                    place = it.place ?: "",
                    date = it.duration ?: "",
                    image = it.image ?: "",
                    detailImage = it.detail ?: "",
                    detailContent = it.detail2 ?: "",
                    url = it.url ?: ""
                )
            } ?: emptyList()

            emit(State.Success(placeEventList))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }
}