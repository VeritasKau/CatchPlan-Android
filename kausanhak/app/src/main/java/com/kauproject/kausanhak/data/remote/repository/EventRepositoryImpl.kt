package com.kauproject.kausanhak.data.remote.repository

import com.kauproject.kausanhak.data.remote.response.GetEventResponse
import com.kauproject.kausanhak.data.remote.service.event.GetEventService
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.EventRepository
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

    override suspend fun fetchEvents() {
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
        }
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
                image = it.image ?: "",
                url = it.url ?: ""
            )
        } ?: emptyList()
    }
    override fun findEvent(eventId: Int) = getAllEventCollections().find { it.id == eventId }!!

    private fun getAllEventCollections(): List<Event>{
        val allEventCollections = mutableListOf<Event>()

        allEventCollections.addAll(musicals)
        allEventCollections.addAll(campings)
        allEventCollections.addAll(dramas)
        allEventCollections.addAll(koreas)
        allEventCollections.addAll(concerts)
        allEventCollections.addAll(exhibitions)
        allEventCollections.addAll(kids)
        allEventCollections.addAll(classics)

        return allEventCollections
    }
    override fun getConcert(): List<Event> = concerts

    override fun getDrama(): List<Event> = dramas

    override fun getExhibition(): List<Event> = exhibitions

    override fun getKids(): List<Event> = kids

    override fun getClassic(): List<Event> = classics

    override fun getKorea(): List<Event> = koreas

    override fun getMusicals(): List<Event> = musicals

    override fun getCampings(): List<Event> = campings
}