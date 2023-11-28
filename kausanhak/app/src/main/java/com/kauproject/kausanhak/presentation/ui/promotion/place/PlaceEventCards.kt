package com.kauproject.kausanhak.presentation.ui.promotion.place

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.presentation.ui.event.EventCards

@Composable
fun PlaceEventCards(
    placeEvent: List<Event>,
    onPlaceClicked: (Int) -> Unit,
    onPlaceArrowClicked: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 16.dp)
        ,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 16.dp)
                    .weight(1f)
                ,
                text = stringResource(id = R.string.promotion_place_title),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            IconButton(
                modifier = Modifier
                ,
                onClick = onPlaceArrowClicked
            ) {
                Icon(
                    modifier = Modifier
                    ,
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null)
            }
        }
        EventCards(events = placeEvent, onEventClick = onPlaceClicked)
    }
}