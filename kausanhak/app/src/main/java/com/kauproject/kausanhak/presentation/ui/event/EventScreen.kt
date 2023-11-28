package com.kauproject.kausanhak.presentation.ui.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kauproject.kausanhak.presentation.anim.lottieanimation.LottieLoadingAnimation
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.ui.chatbot.ChatBotIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG = "EventScreen"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventScreen(
    onEventClick: (Int) -> Unit,
    onArrowClick: (Int) -> Unit,
    navController: NavHostController
){
    val snackbarHostState = remember { SnackbarHostState() }
    val viewModel: EventScreenViewModel = hiltViewModel()
    val collection = viewModel.collection.collectAsState()
    val isError = viewModel.isError.collectAsState()

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember{ mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        viewModel.getEventCollection()
        delay(500)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    LaunchedEffect(isError.value){
        if(isError.value){
            snackbarHostState.showSnackbar(
                message = "Error: " + isError.value,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        bottomBar = {
            CatchPlanBottomBar(navController = navController, currentRoute = BottomNavItem.Event.screenRoute)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {paddingValues ->
        if(collection.value.isEmpty()){
            LottieLoadingAnimation(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            )
        }else{
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(paddingValues)
                    .pullRefresh(refreshState)
            ){
                itemsIndexed(items = collection.value){index, event ->
                    if(index == 0){
                        Box(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                                .height(
                                    if (refreshing) {
                                        50.dp
                                    } else {
                                        lerp(0.dp, 50.dp, refreshState.progress.coerceIn(0f..1f))
                                    }
                                )
                        ){
                            if(refreshing){
                                LottieLoadingAnimation(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)

                                )
                            }
                        }
                    }
                    if(index > 0){
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(top = 20.dp)
                        )
                    }
                    EventCollection(
                        eventCollection = event,
                        onEventClick = onEventClick,
                        onArrowClick = onArrowClick
                    )
                    if(index == 7){
                        Spacer(modifier = Modifier.padding(bottom = 20.dp))
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
}


