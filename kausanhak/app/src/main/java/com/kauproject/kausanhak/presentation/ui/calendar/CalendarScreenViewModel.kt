package com.kauproject.kausanhak.presentation.ui.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.entitiy.MemoEntity
import com.kauproject.kausanhak.domain.repository.EventDateRepository
import com.kauproject.kausanhak.domain.repository.MemoRepository
import com.kauproject.kausanhak.presentation.ui.calendar.data.Events
import com.kauproject.kausanhak.presentation.ui.calendar.data.Memo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    private val eventDateRepository: EventDateRepository,
    private val memoRepository: MemoRepository
): ViewModel() {
    private val _date = MutableStateFlow<Map<LocalDate, List<Events>>>(emptyMap())
    val date = _date.asStateFlow()

    private val _memo = MutableStateFlow<Map<LocalDate, List<Memo>>>(emptyMap())
    val memo = _memo.asStateFlow()

    companion object{
        const val TAG = "CalendarScreenVM"
    }

    init {
        initMemo()
        initDate()
    }

    fun deleteDate(eventId: Int){
        viewModelScope.launch {
            eventDateRepository.deleteEvent(eventId = eventId)
        }
    }

    fun addMemo(content: String, date: String){
        viewModelScope.launch {
            memoRepository.addMemo(
                MemoEntity(
                    date = date,
                    content = content
                )
            )
        }
    }

    fun deleteMemo(no: Int){
        viewModelScope.launch {
            memoRepository.deleteMemo(no)
        }
    }

    private fun mapperToLocalDate(date: String): LocalDateTime {
        return LocalDateTime.parse("${date}T00:00:00")
    }

    private fun initDate(){
        viewModelScope.launch {
            eventDateRepository.readEvent().distinctUntilChanged().collect{ date->
                if(date.isEmpty()){
                    _date.value = emptyMap()
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
                    }.groupBy {
                        it.date.toLocalDate()
                    }
                }
            }
        }
    }

    private fun initMemo(){
        viewModelScope.launch{
            memoRepository.readMemo().collect{ memo->
                Log.d(TAG, "VM Memo value:${memo}")
                if(memo.isEmpty()){
                    _memo.value = emptyMap()
                }else{
                    _memo.value = memo.map { it->
                        Memo(
                            no = it.no,
                            date = mapperToLocalDate(it.date),
                            content = it.content,
                            color = R.color.purple_main
                        )
                    }.groupBy {
                        it.date.toLocalDate()
                    }
                }
                Log.d(TAG, "VM Memo data value:${_memo.value}")
            }
        }
    }

}