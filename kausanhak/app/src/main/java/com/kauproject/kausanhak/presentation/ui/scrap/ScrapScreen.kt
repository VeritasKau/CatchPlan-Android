package com.kauproject.kausanhak.presentation.ui.scrap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.event.EventCard

private val CardWidth = 170.dp
private val CardPadding = 16.dp

@Composable
fun ScrapScreen(
    navController: NavController,
    onEventClick: (Int) -> Unit
){
    val scroll = rememberScrollState(0)
    val gradientWidth = with(LocalDensity.current){
        (6 * (CardWidth + CardPadding).toPx())
    }
    val viewModel: ScrapViewModel = hiltViewModel()
    val scrapEvent = viewModel.scrap.collectAsState()

    Scaffold(
        topBar = { TopBar(backPress = { navController.navigateUp() }) },
        containerColor = Color.White
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
            itemsIndexed(scrapEvent.value){ index, item ->
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
private fun TopBar(
    backPress: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .height(50.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.Center
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
                    contentDescription = null,
                    tint = colorResource(id = R.color.purple_main)
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 36.dp)
                ,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier
                    ,
                    text = stringResource(id = R.string.scrap_top_bar),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.purple_main)
                )
            }
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
    }
}
