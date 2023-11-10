package com.kauproject.kausanhak.presentation.ui.calendar.information

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kauproject.kausanhak.presentation.ui.calendar.CalendarScreenViewModel
import com.kauproject.kausanhak.presentation.ui.calendar.data.Events
import com.kauproject.kausanhak.presentation.ui.calendar.dialog.ShowDeleteDialog
import com.kauproject.kausanhak.presentation.util.clickable

@Composable
fun LazyItemScope.EventInformation(
    events: Events,
    onEventClick: (Int) -> Unit,
    viewModel: CalendarScreenViewModel,
    date: String
){
    var showDeleteDialog by remember { mutableStateOf(false) }


    if(showDeleteDialog){
        ShowDeleteDialog(
            showDialog = {showDeleteDialog = it},
            viewModel = viewModel,
            eventId = events.id
        )
    }

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
                    .width(240.dp)
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp)
            ,
            horizontalAlignment = Alignment.End
        ) {
            Icon(
                modifier = Modifier
                    .clickable {
                        showDeleteDialog = true
                    }
                ,
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
    }
    HorizontalDivider(thickness = 1.dp,color = Color.LightGray)
}