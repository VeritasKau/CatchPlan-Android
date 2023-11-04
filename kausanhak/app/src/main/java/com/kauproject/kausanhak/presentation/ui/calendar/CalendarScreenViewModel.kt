package com.kauproject.kausanhak.presentation.ui.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.data.model.EventDateEntity
import com.kauproject.kausanhak.domain.repository.EventDateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    private val eventDateRepository: EventDateRepository
): ViewModel() {
    private val _date = MutableStateFlow<List<Events>>(emptyList())
    val date = _date.asStateFlow()

    companion object{
        const val TAG = "CalendarScreenVM"
    }

    init {
        initDate()
    }

    private fun mapperToLocalDate(date: String): LocalDateTime {
        return LocalDateTime.parse("${date}T00:00:00")
    }

    fun initDate(){
        viewModelScope.launch(Dispatchers.IO) {
            eventDateRepository.readEvent().distinctUntilChanged().collect{ date->
                if(date.isNullOrEmpty()){
                    Log.d(TAG, "Empty List")
                }else{
                    _date.value = date.map { it ->
                        Events(
                            id = it.eventId,
                            date = mapperToLocalDate(it.date),
                            name = it.name,
                            place = it.place,
                            image = it.image,
                            color = R.color.purple_main
                        )
                    }
                    Log.d(TAG, "Value:$date")
                }
            }
        }
    }

}