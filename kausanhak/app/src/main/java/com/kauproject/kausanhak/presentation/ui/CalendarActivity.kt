package com.kauproject.kausanhak.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kauproject.kausanhak.ui.theme.KausanhakTheme
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import kotlin.time.Duration.Companion.days

class CalendarActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KausanhakTheme {

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultView(){
    CalendarScreen()
}

@Composable
fun CalendarScreen(){
    val currentMonth = remember{ YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember{ currentMonth.plusMonths(100) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val dayOfWeek = remember { daysOfWeek() }
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = dayOfWeek.first()
    )

    Column {
        HorizontalCalendar(
            state = state,
            dayContent = {day ->
                Day(day, isSelected = selectedDate == day.date){day ->
                    selectedDate = if (selectedDate == day.date) null else day.date
                } },
            monthHeader = {month ->
                DayOfMonthTitle(month = month)
                val daysOfWeek = month.weekDays.first().map{ it.date.dayOfWeek }
                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    var sheetState by remember{ mutableStateOf(false) }

    if(sheetState){
        BottomSheet() {
            sheetState = false
        }
    }
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = {
                    sheetState = true
                    onClick(day)
                    Log.d("CalendarActivity", day.date.dayOfMonth.days.toString())
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray
        )
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>){
    Row(modifier = Modifier.fillMaxWidth()){
        for(dayOfWeek in daysOfWeek){
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            )
        }
    }
}

@Composable
fun DayOfMonthTitle(month: CalendarMonth){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = Modifier
                .padding(15.dp)
            ,
            text = month.yearMonth.monthValue.toString(),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit){
    val modalBottomSheetScaffoldState = rememberModalBottomSheetState()

    ModalBottomSheet(
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetScaffoldState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        DateList()
    }

}

@Composable
fun DateList(){
    val date = listOf(
        "2023-09-23 흠뻑쇼",
        "2023-10-02 연예인 콘서트",
        "2023-10-02 연예인 콘서트",
        "2023-10-02 연예인 콘서트",
        "2023-10-02 연예인 콘서트",
        "2023-10-02 연예인 콘서트",
        "2023-10-02 연예인 콘서트"
    )

    LazyColumn {
        items(date.size){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 40.dp)
            ) {
                Text(
                    text = date[it]
                )
            }
        }
    }
}


