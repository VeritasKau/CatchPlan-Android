package com.kauproject.kausanhak.presentation.ui.calendar

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.util.clickable
import com.kauproject.kausanhak.presentation.util.displayText
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
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

const val TAG = "CalendarScreen"

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
    val events = remember(eventsState){
        eventsState.groupBy { it.date.toLocalDate() }
    }
    val eventsInSelectedDate = remember(selection){
        derivedStateOf {
            val date = selection?.date
            if(date == null) emptyList() else events[date].orEmpty()
        }
    }
    var flag by remember{ mutableStateOf(false) }

    // eventsInSelectedDate가 수행된 후 LazyColumn을 수행 (잘못된 index 접근 방지)
    LaunchedEffect(eventsInSelectedDate){
        flag = true
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
                    val colors = if(day.position == DayPosition.MonthDate){
                        events[day.date].orEmpty().map { colorResource(it.color) }
                    }else{
                        emptyList()
                    }
                    Day(
                        day = day,
                        isSelected = selection == day,
                        colors = colors
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
                    events = eventsInSelectedDate.value
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()){
                if(flag){
                    itemsIndexed(items = eventsInSelectedDate.value){index, event->
                        EventInformation(
                            events = event,
                            onEventClick = onEventClick
                        )
                    }
                    flag = false
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
    onClick: (CalendarDay) -> Unit = {},
){
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) colorResource(id = R.color.lavender_3) else Color.LightGray,
            )
            .padding(1.dp)
            .background(color = Color.White)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
    ){
        val textColor = when(day.position){
            DayPosition.MonthDate -> Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> Color.LightGray
        }
        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 3.dp, end = 4.dp),
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 12.sp
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for(color in colors){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(color)
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
                fontWeight = FontWeight.SemiBold,
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
private fun LazyItemScope.EventInformation(
    events: Events,
    onEventClick: (Int) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(80.dp)
            .background(Color.White)
            .clickable { onEventClick(events.id) }
            .padding(vertical = 10.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ){
        Surface(
            modifier = Modifier
                .padding(start = 10.dp, end = 5.dp)
                .size(60.dp)
            ,
            shape = CircleShape,
            color = Color.White
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(events.image)
                    .crossfade(true)
                    .build()
                ,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 5.dp)
            ,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 40.dp)
                ,
                text = events.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = events.place,
                fontWeight = FontWeight.SemiBold,
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }
    }
    HorizontalDivider(thickness = 1.dp,color = Color.LightGray)
}

@Composable
private fun currentDate(
    selection: CalendarDay,
    events: List<Events>
){
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

        }
        if(events.isEmpty()){
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

