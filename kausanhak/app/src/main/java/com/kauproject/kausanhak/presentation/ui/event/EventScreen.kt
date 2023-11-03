package com.kauproject.kausanhak.presentation.ui.event

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.EventCollection
import com.kauproject.kausanhak.presentation.anim.lottieanimation.LottieDotAnimation
import com.kauproject.kausanhak.presentation.anim.lottieanimation.LottieLoadingAnimation
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme

const val TAG = "EventScreen"

@Composable
fun EventScreen(
    onEventClick: (Int) -> Unit,
    onArrowClick: (Int) -> Unit,
    navController: NavHostController
){
    val snackbarHostState = remember { SnackbarHostState() }
    val viewModel: EventScreenViewModel = hiltViewModel()
    val collection by viewModel.eventCollection.collectAsState()
    var isCollection by remember{ mutableStateOf(false) }

    isCollection = collection.isNotEmpty()

    Scaffold(
        bottomBar = {
            CatchPlanBottomBar(navController = navController, currentRoute = BottomNavItem.Event.screenRoute)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {paddingValues ->
        LottieLoadingAnimation(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
            ,
            isCompleted = isCollection
        )
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .background(Color.White)
        ){
            itemsIndexed(
                items = collection
            ){index, event ->
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

}



object EventDestination{
    const val EVENT_DETAIL_ROUTE = "event"
    const val EVENT_DETAIL_ID = "eventId"
    const val EVENT_ARROW_ROUTE = "arrow"
    const val EVENT_ARROW_ID = "arrowId"
}
