package com.kauproject.kausanhak.presentation.ui.scrap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.ScrapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val scrapRepository: ScrapRepository
): ViewModel() {
    private val _scrap = MutableStateFlow<List<Event>>(emptyList())
    val scrap = _scrap.asStateFlow()

    init {
        initScrap()
    }

    fun initScrap(){
        viewModelScope.launch {
            scrapRepository.readScrap().collect{ scrap->
                if(scrap.isEmpty()){
                    _scrap.value = emptyList()
                }else{
                    _scrap.value = scrap.map {
                        Event(
                            id = it.eventId,
                            date = it.date,
                            place = it.place,
                            name = it.name,
                            image = it.image,
                            detailContent = "",
                            url = "",
                            detailImage = ""
                        )
                    }
                }

            }
        }
    }
}