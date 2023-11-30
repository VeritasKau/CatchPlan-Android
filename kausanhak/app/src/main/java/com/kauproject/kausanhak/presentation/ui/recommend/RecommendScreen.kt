package com.kauproject.kausanhak.presentation.ui.recommend

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.anim.lottieanimation.LottieBackgroundAnimation
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.ui.chatbot.ChatBotIcon
import uz.yusufbekibragimov.swipecard.SwipeCard

private val colors
    @Composable
    get() = listOf(
        colorResource(id = R.color.rose_4),
        colorResource(id = R.color.lavender_3),
        Color.White,
    )

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendScreen(
    navController: NavHostController,
    onEventClicked: (Int) -> Unit
){
    val viewModel: RecommendScreenViewModel = hiltViewModel()
    val list = viewModel.recommendList.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = colors))
        ,
        bottomBar = { CatchPlanBottomBar(navController = navController, currentRoute = BottomNavItem.Recommend.screenRoute) },
        containerColor = Color.Transparent,
        contentColor = Color.Transparent
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(list.value.isNotEmpty()){
                SwipeCard(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                    ,
                    itemsList = list.value,
                    heightCard = 500.dp,
                    orientation = Orientation.Horizontal
                ) { event->
                    RecommendCard(
                        event = event,
                        onEventClicked = onEventClicked,
                        modifier = Modifier
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

