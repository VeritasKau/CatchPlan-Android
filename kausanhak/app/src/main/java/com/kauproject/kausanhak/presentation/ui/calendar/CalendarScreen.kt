package com.kauproject.kausanhak.presentation.ui.calendar

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.ui.calendar.data.Events
import com.kauproject.kausanhak.presentation.ui.calendar.data.Memo
import com.kauproject.kausanhak.presentation.ui.calendar.dialog.ShowMemoDialog
import com.kauproject.kausanhak.presentation.ui.calendar.information.EventInformation
import com.kauproject.kausanhak.presentation.ui.calendar.information.MemoInformation
import com.kauproject.kausanhak.presentation.util.clickable
import com.kauproject.kausanhak.presentation.util.rememberFirstCompletelyVisibleMonth
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

const val TAG = "CalendarScreen"

/*
캘린더 메인화면
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavHostController,
    onEventClick: (Int) -> Unit
){
    val currentMonth = remember{ YearMonth.now() }
    val startMonth = remember{ currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    var selection by remember {
        mutableStateOf<CalendarDay?>(CalendarDay(date = LocalDate.now(), position = DayPosition.MonthDate))
    }
    val daysOfWeek = remember { daysOfWeek() }
    val viewModel: CalendarScreenViewModel = hiltViewModel()

    val eventsState by viewModel.date.collectAsState()
    val eventsInSelectedDate by remember(eventsState, selection) {
        mutableStateOf<List<Events>>(if (selection?.date == null) emptyList() else eventsState[selection?.date].orEmpty())
    }

    val memoState by viewModel.memo.collectAsState()
    val memoInSelectedDate by remember(memoState, selection) {
        mutableStateOf<List<Memo>>(if (selection?.date == null) emptyList() else memoState[selection?.date].orEmpty())
    }

    var eventFlag by remember{ mutableStateOf(false) }
    var memoFlag by remember{ mutableStateOf(false) }

    LaunchedEffect(eventsInSelectedDate, memoInSelectedDate){
        Log.d(TAG, "EVENT FLAG TRUE")
        eventFlag = true
    }
    LaunchedEffect(eventsInSelectedDate, memoInSelectedDate){
        Log.d(TAG, "MEMO FLAG TRUE")
        memoFlag = true
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
        ,
        bottomBar = {
            CatchPlanBottomBar(navController = navController, currentRoute = BottomNavItem.Calendar.screenRoute)
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            val state = rememberCalendarState(
                startMonth = startMonth,
                endMonth = endMonth,
                firstVisibleMonth = currentMonth,
                firstDayOfWeek = daysOfWeek.first(),
                outDateStyle = OutDateStyle.EndOfGrid
            )
            val coroutineScope = rememberCoroutineScope()
            val visibleMonth = rememberFirstCompletelyVisibleMonth(state)

            LaunchedEffect(visibleMonth){
                if(selection != CalendarDay(date = LocalDate.now(), position = DayPosition.MonthDate)){
                    selection = CalendarDay(
                        date = state.firstVisibleMonth.yearMonth.atStartOfMonth(),
                        position = DayPosition.MonthDate)
                }
            }

            CalendarTitle(
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                currentMonth = visibleMonth.yearMonth,
                goToPrevious = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                    }
                },
                goToNext = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                    }
                },
            )

            HorizontalCalendar(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(Color.Transparent)
                ,
                state = state,
                dayContent = {day->
                    val colors =
                        if(day.position == DayPosition.MonthDate){
                            eventsState[day.date].orEmpty().map { colorResource(it.color) }
                        }else{
                            emptyList()
                        }
                    val memoColors =
                        if(day.position == DayPosition.MonthDate){
                            memoState[day.date].orEmpty().map { colorResource(id = it.color) }
                        }else{
                            emptyList()
                        }

                    Day(
                        day = day,
                        isSelected = selection == day,
                        colors = colors,
                        memoColors = memoColors
                    ){ clicked->
                        selection = clicked
                    }
                },
                monthHeader = {
                    MonthHeader(
                        modifier = Modifier.padding(vertical = 8.dp),
                        daysOfWeek = daysOfWeek,
                    )
                }
            )
            HorizontalDivider(color = Color.LightGray)

            selection?.let {
                currentDate(
                    selection = it,
                    events = eventsInSelectedDate,
                    viewModel = viewModel,
                    memoSelectableDates = memoInSelectedDate
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth().wrapContentWidth(),
            ){
                if(memoFlag){
                    itemsIndexed(items = memoInSelectedDate){ _, memo ->
                        key(memo.no) {
                            MemoInformation(memo = memo, viewModel = viewModel)
                        }
                    }
                    memoFlag = false
                }
            }


            LazyColumn(modifier = Modifier.fillMaxSize()){
                if(eventFlag){
                    itemsIndexed(items = eventsInSelectedDate) { _, event->
                        key(event.id) {
                            EventInformation(
                                events = event,
                                onEventClick = onEventClick,
                                viewModel = viewModel,
                                date = selection?.date.toString()
                            )
                        }
                    }
                    eventFlag = false
                }
            }

        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean = false,
    colors: List<Color> = emptyList(),
    memoColors: List<Color> = emptyList(),
    onClick: (CalendarDay) -> Unit = {},
){
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .background(color = Color.White)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = {
                    onClick(day)
                    Log.d(TAG, "Click:${day.date}")
                }
            ),
    ){
        val textColor = when(day.position){
            DayPosition.MonthDate -> Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> Color.LightGray
        }
        val selectDayColor = if(isSelected) colorResource(id = R.color.lavender_3) else Color.Transparent
        val selectTextColor = if(isSelected) Color.White else Color.Unspecified

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .background(color = selectDayColor)
                    .align(Alignment.CenterHorizontally)
                ,
            ){
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                    ,
                    text = day.date.dayOfMonth.toString(),
                    color = if(isSelected) selectTextColor else textColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            if(colors.isNotEmpty() || memoColors.isNotEmpty()){
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(6.dp)
                        .background(color = colorResource(id = R.color.purple_main))
                    ,
                )
            }else{
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(6.dp)
                        .background(color = Color.Transparent)
                    ,
                )

            }
        }

    }

}

@Composable
private fun MonthHeader(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek> = emptyList()
){
    Row(modifier = modifier
        .fillMaxWidth()
        .background(color = colorResource(id = R.color.purple_calendar_bar))
    ){
        for(dayOfWeek in daysOfWeek){
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
                ,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.White,
                text = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN),
                fontWeight = FontWeight.Bold,
            )
        }
    }

}
@Composable
fun CalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
){
    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = "Previous",
            onClick = goToPrevious
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .testTag("MonthTitle"),
            text = currentMonth.month.value.toString() + "월",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = "Next",
            onClick = goToNext
        )
    }

}

@Composable
private fun CalendarNavigationIcon(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit
) = Box(
    modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
){
    Icon(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .align(Alignment.Center),
        painter = icon,
        contentDescription = contentDescription
    )

}

@Composable
private fun currentDate(
    selection: CalendarDay,
    events: List<Events>,
    viewModel: CalendarScreenViewModel,
    memoSelectableDates: List<Memo>,
){
    var showMemoDialog by remember { mutableStateOf(false) }

    if(showMemoDialog){
        ShowMemoDialog(
            showDialog = { showMemoDialog = it },
            viewModel = viewModel,
            date = selection.date.toString()
        )
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White)
            ,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 10.dp, end = 5.dp)
                ,
                painter = painterResource(id = R.drawable.ic_app_icon),
                contentDescription = null
            )

            Text(
                text = selection.date.dayOfMonth.toString() + "." + selection.date.dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN) + "요일",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.purple_main)
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                horizontalAlignment = Alignment.End
            ) {
                IconButton(
                    onClick = {
                        showMemoDialog = true
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 10.dp)
                        ,
                        imageVector = Icons.Outlined.AddCircle,
                        contentDescription = null,
                        tint = colorResource(id = R.color.lavender_3)
                    )

                }
                
            }

        }
        if(events.isEmpty() && memoSelectableDates.isEmpty()){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(start = 5.dp)
                ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(20.dp)
                    ,
                    painter = painterResource(id = R.drawable.ic_vertical_bar),
                    contentDescription = null)
                Text(
                    text = stringResource(id = R.string.calendar_no_schedule),
                    color = colorResource(id = R.color.gray_0),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}





