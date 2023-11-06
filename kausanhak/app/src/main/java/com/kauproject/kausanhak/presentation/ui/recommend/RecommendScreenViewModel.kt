package com.kauproject.kausanhak.presentation.ui.recommend

import androidx.lifecycle.ViewModel
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository
): ViewModel() {
    fun findEvent(eventId: Int): Event{
        return eventRepository.findEvent(eventId = eventId)
    }

    fun getRecommendEvent(mbti: String): List<Event>{
        return when(mbti){
            "ENFP" -> {
                return listOf(
                    findEvent(224), findEvent(229), findEvent(236), findEvent(237),
                    findEvent(238), findEvent(225), findEvent(6), findEvent(62)
                )
            }
            "ENFJ" -> {
                return listOf(
                    findEvent(224), findEvent(229), findEvent(236), findEvent(237),
                    findEvent(238), findEvent(225), findEvent(6), findEvent(62)
                )
            }
            else -> { return emptyList() }
        }
    }
}