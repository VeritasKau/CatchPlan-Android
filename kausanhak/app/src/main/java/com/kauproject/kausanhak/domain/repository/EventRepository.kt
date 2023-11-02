package com.kauproject.kausanhak.domain.repository

import com.kauproject.kausanhak.domain.model.Event

interface EventRepository {
    suspend fun fetchEvents()
    fun getMusicals(): List<Event>
    fun getCampings(): List<Event>
    fun findEvent(eventId: Int): Event
    fun getConcert(): List<Event>
    fun getDrama(): List<Event>
    fun getExhibition(): List<Event>
    fun getKids(): List<Event>
    fun getClassic(): List<Event>
    fun getKorea(): List<Event>
}