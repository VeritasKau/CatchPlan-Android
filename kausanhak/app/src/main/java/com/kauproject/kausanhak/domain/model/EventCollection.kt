package com.kauproject.kausanhak.domain.model

data class EventCollection(
    val id: Int = -1,
    val name: String = "",
    val events: List<Event> = emptyList()
)

object EventRepo{
    fun getEvents(): List<EventCollection> = eventCollections
    fun getEvent(eventId: Int) = getAllEventCollections().find { it.id == eventId }!!

}

private val musical = EventCollection(
    id = 1,
    name = "뮤지컬",
    events = mockMusicalEvents
)

private val concert = EventCollection(
    id = 2,
    name = "콘서트",
    events = mockConcertEvents
)

private val theater = EventCollection(
    id = 3,
    name = "연극",
    events = mockTheaterEvents
)

private val eventCollections = listOf(
    musical,
    concert,
    theater
)



private fun getAllEventCollections(): List<Event>{
    val allEventCollections = mutableListOf<Event>()

    allEventCollections.addAll(musical.events)
    allEventCollections.addAll(concert.events)
    allEventCollections.addAll(theater.events)

    return allEventCollections
}
