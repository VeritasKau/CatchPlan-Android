package com.kauproject.kausanhak.domain.model

data class EventCollection(
    val id: Long,
    val name: String,
    val events: List<Event>
)

object EventRepo{
    fun getEvents(): List<EventCollection> = eventCollections
    fun getEvent(eventId: Long) = mockList().find { it.id == eventId }!!
    fun getMockEventList() = mockList()
    fun getMockEvents() = mockList()
    fun getMusical(eventId: Long) = mockMusicalEvents.find{ it.id == eventId }
    fun getConcert(eventId: Long) = mockConcertEvents.find{ it.id == eventId }
    fun getTheater(eventId: Long) = mockTheaterEvents.find{ it.id == eventId }

}

private val musical = EventCollection(
    id = 1L,
    name = "뮤지컬",
    events = mockMusicalEvents
)

private val concert = EventCollection(
    id = 2L,
    name = "콘서트",
    events = mockConcertEvents
)

private val theater = EventCollection(
    id = 3L,
    name = "연극",
    events = mockTheaterEvents
)

private val eventCollections = listOf(
    musical,
    concert,
    theater
)

fun mockList() = listOf(
        Event(0L, "블랙핑크", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
        Event(1L, "A", "올림픽홀", "2023-09-29", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
        Event(2L, "B", "올림픽홀", "2023-09-21", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
        Event(3L, "C", "올림픽홀", "2023-10-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
        Event(4L, "D", "올림픽홀", "2023-12-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
        Event(5L, "E", "올림픽홀", "2023-09-13", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
        Event(6L, "F", "올림픽홀", "2023-09-03", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
        Event(7L, "G", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
        Event(8L, "H", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804")
    )
