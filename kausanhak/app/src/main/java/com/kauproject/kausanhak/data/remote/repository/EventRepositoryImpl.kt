package com.kauproject.kausanhak.data.remote.repository

import com.kauproject.kausanhak.data.remote.response.GetEventResponse
import com.kauproject.kausanhak.data.remote.service.event.GetEventService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventCollection
import com.kauproject.kausanhak.domain.repository.EventRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class EventRepositoryImpl(
    private val getEventService: GetEventService
): EventRepository {
    private var musicals = emptyList<Event>()
    private var campings = emptyList<Event>()
    private var classics = emptyList<Event>()
    private var concerts = emptyList<Event>()
    private var dramas = emptyList<Event>()
    private var exhibitions = emptyList<Event>()
    private var kids = emptyList<Event>()
    private var koreas = emptyList<Event>()
    private var eventCollection = emptyList<EventCollection>()

    override fun fetchEvents(): Flow<State<List<EventCollection>>> = flow {
        emit(State.Loading)

        val musicalsResponse = getEvent("musical")
        val campingsResponse = getEvent("camping")
        val classicResponse = getEvent("classic")
        val concertResponse = getEvent("concert")
        val dramaResponse = getEvent("drama")
        val exhibitionResponse = getEvent("exhibition")
        val kidsResponse = getEvent("kids")
        val koreaResponse = getEvent("korea")

        if (musicalsResponse.code() == 200 && campingsResponse.code() == 200 &&
            classicResponse.code() == 200 && concertResponse.code() == 200 &&
            dramaResponse.code() == 200 && exhibitionResponse.code() == 200 &&
            kidsResponse.code() == 200 && koreaResponse.code() == 200
            ) {
            musicals = parseResponse(musicalsResponse)
            campings = parseResponse(campingsResponse)
            classics = parseResponse(classicResponse)
            concerts = parseResponse(concertResponse)
            dramas = parseResponse(dramaResponse)
            exhibitions = parseResponse(exhibitionResponse)
            kids = parseResponse(kidsResponse)
            koreas = parseResponse(koreaResponse)

            val concert = EventCollection(
                id = -1,
                name = "콘서트",
                events = concerts
            )
            val exhibition = EventCollection(
                id = -2,
                name = "연극",
                events = exhibitions
            )
            val musical = EventCollection(
                id = -3,
                name = "뮤지컬",
                events = musicals
            )
            val drama = EventCollection(
                id = -4,
                name = "드라마",
                events = dramas
            )
            val camping = EventCollection(
                id = -5,
                name = "캠핑/레저",
                events = campings
            )
            val korea = EventCollection(
                id = -6,
                name = "지역행사",
                events = koreas
            )
            val classic = EventCollection(
                id = -7,
                name = "클래식",
                events = classics
            )
            val kid = EventCollection(
                id = -8,
                name = "아동/가족",
                events = kids
            )

            eventCollection = listOf(
                concert, exhibition, musical, drama, camping, korea, classic, kid
            )
            emit(State.Success(eventCollection))
        }
        else{
            emit(State.ServerError(musicalsResponse.code()))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    private suspend fun getEvent(event: String): Response<List<GetEventResponse>> {
        return getEventService.getEvent(event)
    }

    private fun parseResponse(response: Response<List<GetEventResponse>>): List<Event> {
        return response.body()?.map {
            Event(
                id = it.id ?: 0,
                name = it.text ?: "",
                place = it.place ?: "",
                date = it.duration ?: "",
                detailImage = it.detail ?: "",
                detailContent = it.detail2 ?: "",
                image = it.image ?: "",
                url = it.url ?: ""
            )
        } ?: emptyList()
    }
    override fun findEvent(eventId: Int) = getAllEventCollections().find { it.id == eventId }!!

    override fun findEventCollection(eventCollectionId: Int): EventCollection = eventCollection.find { it.id == eventCollectionId }!!

    private fun getAllEventCollections(): List<Event>{
        val allEventCollections = mutableListOf<Event>()

        eventCollection.forEach { it->
            allEventCollections.addAll(it.events)
        }

        return allEventCollections
    }
}