package com.kauproject.kausanhak.presentation.ui.promotion.place

import androidx.lifecycle.ViewModel
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.PlaceEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val placeEventRepository: PlaceEventRepository
): ViewModel(){
    fun getPlaceEvent(): List<Event>{
        return placeEventRepository.getPlaceEvent()
    }
}