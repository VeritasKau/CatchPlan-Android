package com.kauproject.kausanhak.domain.model

data class EventCollection(
    val id: Int = -1000,
    val name: String = "",
    val events: List<Event> = emptyList()
)
