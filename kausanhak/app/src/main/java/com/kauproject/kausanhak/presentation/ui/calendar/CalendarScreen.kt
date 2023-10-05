package com.kauproject.kausanhak.presentation.ui.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.YearMonth

private val events = eventsList().groupBy { it.time.toLocalDate() }

@Composable
fun CalendarScreen(){
    val currentMonth = remember{ YearMonth.now() }
    val startMonth = remember{ currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val selection by remember { mutableStateOf<CalendarDay?>(null) }
    val dayOfWeek = remember { daysOfWeek() }
    val eventsInSelectedDate = remember{
        derivedStateOf {
            val date = selection?.date
            if(date == null) emptyList() else events[date].orEmpty()
        }
    }



}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarScreen(){
    CalendarScreen()
}