package com.kauproject.kausanhak.presentation.ui.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.EventCollection
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme

private val CardWidth = 170.dp
private val CardPadding = 16.dp

@Composable
fun EventListScreen(
    eventCollectionId: Int,
    onEventClick: (Int) -> Unit,
    navController: NavController
) {
    val viewModel: EventListScreenViewModel = hiltViewModel()
    val eventCollection = remember(eventCollectionId){ viewModel.findEventCollection(eventCollectionId) }
    val scroll = rememberScrollState(0)
    val gradientWidth = with(LocalDensity.current){
        (6 * (CardWidth + CardPadding).toPx())
    }


    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
        ,
        topBar = { EventListTopBar(
            eventCollection = eventCollection,
            backPress = { navController.navigateUp() }
        ) }
    ) {paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.White)
            ,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ){
            itemsIndexed(eventCollection.events){ index, item ->  
                EventCard(
                    modifier = Modifier
                        .padding(15.dp)
                    ,
                    event = item,
                    index = index,
                    onEventClick = onEventClick,
                    gradientWidth = gradientWidth,
                    scroll = scroll.value
                )
            }

        }

    }
}

@Composable
private fun EventListTopBar(
    eventCollection: EventCollection,
    backPress: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .height(50.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                ),
            onClick = backPress
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.detail_back),
                tint = colorResource(id = R.color.purple_main)
            )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 36.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                ,
                text = eventCollection.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.purple_main)
            )
        }

    }

}


@Preview(showBackground = true)
@Composable
private fun PreviewTopBar(){
    val navController = rememberNavController()
    KausanhakTheme {
        EventListTopBar(eventCollection = EventCollection(name = "콘서트"), backPress = {})
    }
}