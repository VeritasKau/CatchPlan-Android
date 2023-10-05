package com.kauproject.kausanhak.presentation.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.YearMonth

private val events = eventsList().groupBy { it.time.toLocalDate() }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(){
    val currentMonth = remember{ YearMonth.now() }
    val startMonth = remember{ currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val selection by remember { mutableStateOf<CalendarDay?>(null) }
    val daysOfWeek = remember { daysOfWeek() }
    val eventsInSelectedDate = remember{
        derivedStateOf {
            val date = selection?.date
            if(date == null) emptyList() else events[date].orEmpty()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
            outDateStyle = OutDateStyle.EndOfGrid
        )
        val coroutineScope = rememberCoroutineScope()


    }



}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarScreen(){
    CalendarScreen()
}