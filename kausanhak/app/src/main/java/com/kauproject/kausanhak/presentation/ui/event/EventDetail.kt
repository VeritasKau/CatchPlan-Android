package com.kauproject.kausanhak.presentation.ui.event

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventRepo
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.max
import kotlin.math.min
import androidx.compose.ui.unit.lerp as lerp

private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinImageOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)


private val colors
    @Composable
    get() = listOf(
        colorResource(id = R.color.lavender_3),
        colorResource(id = R.color.rose_4)
    )

@Composable
fun EventDetailScreen(
    eventId: Int,
    navController: NavController
){
    //val event = remember(eventId) { EventRepo.getEvent(eventId) }
    var showDatePicker by remember { mutableStateOf(false) }
    var setDate by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val viewModel: EventDetailViewModel = hiltViewModel()
    val event = remember(eventId) { viewModel.findEvent(eventId) }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            EventDetailBottomBar(
                event = event,
                onDateSelected = { showDatePicker = it },
            )
        },

    ) {paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .background(Color.White)
            .fillMaxSize()
        ){
            val scroll = rememberScrollState(0)
            Header()
            Body(scroll = scroll, event = event)
            Title(event){ scroll.value }
            Image(event) { scroll.value }
            BackArrow(backPress = {navController.navigateUp()})

            if(showDatePicker){
                EventDatePickerDialog(
                    onDateSelected = { viewModel.addEventDate(event.id, it, event.name, event.place) },
                    onDismiss = { showDatePicker = false },
                    event = event,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }



}

@Composable
private fun EventDetailBottomBar(
    event: Event,
    onDateSelected: (Boolean) -> Unit
){
    var selected by remember { mutableStateOf(false) }
    val isScrap =
        if(selected) painterResource(id = R.drawable.ic_scrap_abled)
        else painterResource(id = R.drawable.ic_scrap_enabled)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(60.dp)
        ,
        verticalArrangement = Arrangement.Center
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.Center
        ){
            IconButton(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(30.dp)
                ,
                onClick = { selected = !selected },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = colorResource(id = R.color.purple_main),
                )
            ) {
                Icon(painter = isScrap, contentDescription = null)

            }
            OutlinedButton(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .weight(0.4f)
                ,
                shape = RoundedCornerShape(7.dp),
                border = BorderStroke(0.5.dp, colorResource(id = R.color.purple_main)),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = stringResource(id = R.string.detail_link),
                    color = colorResource(id = R.color.purple_main),
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .weight(0.4f)
                ,
                shape = RoundedCornerShape(7.dp),
                onClick = { onDateSelected(true) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.purple_main)
                )
            ) {
                Text(
                    modifier = Modifier
                    ,
                    text = stringResource(id = R.string.detail_date_pick),
                    fontWeight = FontWeight.Bold
                )

            }
        }

    }
}
@Composable
private fun Header(){
    Spacer(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(colors))
    )
}

@Composable
private fun Body(
    scroll: ScrollState,
    event: Event
){
    Column(
        modifier = Modifier
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier
                .verticalScroll(scroll)
                .background(Color.Transparent)
        ) {
            Spacer(Modifier.height(GradientScroll))
            Surface(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                ) {
                    Spacer(modifier = Modifier.height(ImageOverlap))
                    Spacer(modifier = Modifier.height(TitleHeight))
                    Spacer(modifier = Modifier.height(16.dp))

                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(event.detailImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.sample),
                        contentScale = ContentScale.Crop
                    )
                }

            }




        }
    }

}

@Composable
private fun Title(event: Event, scrollProvider: () -> Int){
    val maxOffset = with(LocalDensity.current){ MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current){ MinTitleOffset.toPx() }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(Color.White)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = event.name,
            fontSize = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .width(200.dp)
        )
        Text(
            text = event.date,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(vertical = 10.dp)
        )
        HorizontalDivider()

    }

}

@Composable
private fun Image(
    event: Event,
    scrollProvider: () -> Int
){
    val collapseRange = with(LocalDensity.current){ (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ){
        Surface(
            color = Color.LightGray,
            shape = CircleShape,
            modifier = Modifier
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(event.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.sample),
                contentScale = ContentScale.Crop
            )

        }

    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Layout(
        modifier = modifier,
        content = content
    ){ measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()
        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2,
            constraints.maxWidth - imageWidth,
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ){
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }

}

@Composable
private fun BackArrow(
    backPress: () -> Unit
){
    IconButton(
        onClick = backPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = colorResource(id = R.color.purple_main).copy(alpha = 0.3f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.detail_back),
            tint = Color.White
        )
        
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EventDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    event: Event,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
){
    val LAVENDAR = colorResource(id = R.color.lavender_3)
    val dateScope = event.date.length
    val date = event.date
    val start = convertDateToMills(date.firstYear(), date.firstMonth(), date.firstDay())
    val startEnd = convertDateToMills(date.firstYear(), date.firstMonth(), date.firstDay()+1)
    var end: Long? = null


    if(dateScope >= 11){
        end = convertDateToMills(date.secondYear(), date.secondMonth(), date.secondDay())
    }
    
    // 최초 날짜 설정
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = startEnd,
        initialDisplayedMonthMillis = start,
        selectableDates = object: SelectableDates{
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return if(dateScope >= 11){
                (utcTimeMillis > start) && (utcTimeMillis < end!!)
            }else{
                (utcTimeMillis > start) && (utcTimeMillis < startEnd)
            }
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    val complete = stringResource(id = R.string.snack_confirm)

    DatePickerDialog(
        // 색상 커스텀
        colors = DatePickerDefaults.colors(
            containerColor = Color.White,
        )
        ,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = complete,
                            duration = SnackbarDuration.Short
                        )
                    }
                    onDateSelected(selectedDate)
                    onDismiss()
                }

            ) {
                Text(text = "확인")
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                onClick = {
                    onDismiss()
                }){
                Text(text = "취소")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = LAVENDAR,
                todayDateBorderColor = LAVENDAR,
                todayContentColor = Color.Black
            )
        )
    }
}




private fun convertMillisToDate(millis: Long): String {
    return Instant.ofEpochMilli(millis)
        .atOffset(ZoneOffset.ofHours(9))
        .format(DateTimeFormatter.ofPattern("uuuu/MM/dd"))
}

private fun convertDateToMills(year: Int, month: Int, day: Int): Long {
    val date = LocalDateTime.of(year, month, day, 0, 0)
    return date.toInstant(ZoneOffset.ofHours(9)).toEpochMilli()
}

private fun String.firstYear(): Int{
    return this.substring(0, 4).toInt()
}

private fun String.firstMonth(): Int{
    return this.substring(5, 7).toInt()
}

private fun String.firstDay(): Int{
    return this.substring(8, 10).toInt()
}

private fun String.secondYear(): Int{
    return this.substring(13, 17).toInt()
}

private fun String.secondMonth(): Int{
    return this.substring(18, 20).toInt()
}

private fun String.secondDay(): Int{
    return this.substring(21, 23).toInt()
}

@Preview(showBackground = true)
@Composable
fun PreviewEventDetailScreen(){
    val navController = rememberNavController()
    KausanhakTheme {
        EventDetailScreen(eventId = 0, navController = navController)
    }
}