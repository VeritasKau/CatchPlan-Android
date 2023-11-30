package com.kauproject.kausanhak.presentation.ui.recommend

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventCollection
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.ui.chatbot.ChatBotIcon
import com.kauproject.kausanhak.presentation.ui.event.EventCard
import com.kauproject.kausanhak.presentation.ui.promotion.PromotionCard
import uz.yusufbekibragimov.swipecard.SwipeCard

private val CardWidth = 170.dp
private val CardPadding = 16.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendScreen(
    navController: NavHostController
){
    val viewModel: RecommendScreenViewModel = hiltViewModel()
    val list = viewModel.list.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { CatchPlanBottomBar(navController = navController, currentRoute = BottomNavItem.Recommend.screenRoute) },
        containerColor = Color.White
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(list.value.isNotEmpty()){
                SwipeCard(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                    ,
                    itemsList = list.value,
                    heightCard = 600.dp,
                    orientation = Orientation.Horizontal
                ) { event->
                    PromotionCard(
                        event = event,
                        onEventClicked = {},
                        modifier = Modifier.height(600.dp)
                    )
                }

            }
        }
    }
    Box(
        modifier = Modifier
            .padding(bottom = 60.dp)
    ){
        ChatBotIcon(navController = navController)
    }

}

@Composable
private fun TopBar(
    name: String
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                ,
                text = name +"님이 좋아할만한 행사",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.purple_main)
            )
        }

    }

}
