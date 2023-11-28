package com.kauproject.kausanhak.presentation.ui.promotion

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.EventRepository
import com.kauproject.kausanhak.domain.repository.PromotionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotionListViewModel @Inject constructor(
    private val promotionRepository: PromotionRepository,
    private val eventRepository: EventRepository
): ViewModel() {
    fun getPromotionList(): List<Event>{
        return promotionRepository.getPromotion()
    }

    fun fetchEvent(){
        viewModelScope.launch {
            eventRepository.fetchEvents().collectLatest{}
        }
    }

    fun fetchPromotion(){
        viewModelScope.launch {
            promotionRepository.fetchPromotion().collectLatest {}
        }
    }
}