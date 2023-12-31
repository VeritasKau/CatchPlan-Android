package com.kauproject.kausanhak.data.remote.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import com.kauproject.kausanhak.data.remote.response.GetEventResponse
import com.kauproject.kausanhak.data.remote.response.GetPlaceResponse
import com.kauproject.kausanhak.data.remote.response.GetPromotionResponse
import com.kauproject.kausanhak.data.remote.service.event.GetEventService
import com.kauproject.kausanhak.data.remote.service.event.GetPlaceEventService
import com.kauproject.kausanhak.data.remote.service.promotion.GetPromotionService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventCollection
import com.kauproject.kausanhak.domain.repository.EventRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class EventRepositoryImpl(
    private val getEventService: GetEventService,
    private val getPromotionService: GetPromotionService
): EventRepository {
    private var musicals = emptyList<Event>()
    private var campings = emptyList<Event>()
    private var classics = emptyList<Event>()
    private var concerts = emptyList<Event>()
    private var dramas = emptyList<Event>()
    private var exhibitions = emptyList<Event>()
    private var kids = emptyList<Event>()
    private var koreas = emptyList<Event>()
    private var promotions = emptyList<Event>()
    private var eventCollection: MutableList<EventCollection> = mutableListOf()
    private val allEventCollections: MutableList<Event> = mutableListOf()

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
        val promotionResponse = getPromotionService.getPromotion()

        if (musicalsResponse.code() == 200 && campingsResponse.code() == 200 &&
            classicResponse.code() == 200 && concertResponse.code() == 200 &&
            dramaResponse.code() == 200 && exhibitionResponse.code() == 200 &&
            kidsResponse.code() == 200 && koreaResponse.code() == 200 && promotionResponse.code() == 200
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
                name = "레저/캠핑",
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

            promotions = promotionResponse.body()?.map {
                Event(
                    id = (it.id?.times(1000)) ?: 0,
                    name = it.text ?: "",
                    place = it.place ?: "",
                    date = it.duration ?: "",
                    image = it.image ?: "",
                    detailImage = it.detail ?: "",
                    detailContent = it.detail2 ?: "",
                    url = it.url ?: ""
                )
            } ?: emptyList()

            allEventCollections.addAll(promotions)
            eventCollection = listOf(
                concert, exhibition, musical, drama, camping, korea, classic, kid
            ).toMutableList()
            eventCollection.forEach { it ->
                allEventCollections.addAll(it.events)
            }
            emit(State.Success(eventCollection))
        } else {
            emit(State.ServerError(musicalsResponse.code()))
        }
    }.catch { e ->
        emit(State.Error(e))
    }

    private suspend fun getEvent(event: String): Response<List<GetEventResponse>> {
        return getEventService.getEvent(event)
    }

    private fun parseResponse(response: Response<List<GetEventResponse>>): List<Event> {
        return response.body()?.filter { it.status == 1 }?.map {
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

    override fun findEvent(eventId: Int): Event? = allEventCollections.find { it.id == eventId }

    override fun findEventCollection(eventCollectionId: Int): EventCollection =
        eventCollection.find { it.id == eventCollectionId }!!

    override fun findEventCategory(category: String): EventCollection? {
        return if (category != "") {
            Log.d("TEST LOG", "$category")
            eventCollection.find { it.name == category }!!
        } else {
            null
        }
    }
}