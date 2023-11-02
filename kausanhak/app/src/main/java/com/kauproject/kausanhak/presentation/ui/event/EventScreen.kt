package com.kauproject.kausanhak.presentation.ui.event

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.EventCollection
import com.kauproject.kausanhak.domain.model.EventRepo
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import kotlinx.coroutines.launch

const val TAG = "EventScreen"
@Composable
fun EventScreen(
    onEventClick: (Int) -> Unit,
    navController: NavHostController
){
    //val collection = remember{ EventRepo.getEvents() }
    val snackbarHostState = remember { SnackbarHostState() }
    val viewModel: EventScreenViewModel = hiltViewModel()
    val collection by viewModel.eventCollection.collectAsState()
    val scope = rememberCoroutineScope()


    Scaffold(
        bottomBar = {
            CatchPlanBottomBar(navController = navController, currentRoute = BottomNavItem.Event.screenRoute)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {paddingValues ->
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
                    onEventClick = onEventClick
                )
            }
        }
    }

}

object EventDestination{
    const val EVENT_ROUTE = "events"
    const val EVENT_DETAIL_ROUTE = "event"
    const val EVENT_DETAIL_ID = "eventId"
}

@Preview(showBackground = true)
@Composable
fun PreviewEventScreen(){
    val navController = rememberNavController()
    KausanhakTheme {
        EventScreen(onEventClick = {}, navController = navController)
    }
}