package com.kauproject.kausanhak.presentation.ui.event

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kauproject.kausanhak.domain.model.EventCollection
import com.kauproject.kausanhak.domain.model.EventRepo
import com.kauproject.kausanhak.ui.theme.KausanhakTheme

@Composable
fun EventScreen(){
    val navController = rememberNavController()
    val collection = remember{ EventRepo.getEvents() }

    NavHost(
        navController = navController,
        startDestination = EventDestination.EVENT_ROUTE){

        composable(EventDestination.EVENT_ROUTE){
            Events(
                onEventClick = {id: Long ->
                    navController.navigate("${EventDestination.EVENT_DETAIL_ROUTE}/$id")
                },
                eventCollection = collection)
        }

        composable(
            route = "${EventDestination.EVENT_DETAIL_ROUTE}/{${EventDestination.EVENT_DETAIL_ID}}",
            arguments = listOf(navArgument(EventDestination.EVENT_DETAIL_ID){
                type = NavType.LongType }
            )
        ){backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val eventId = arguments.getLong(EventDestination.EVENT_DETAIL_ID)

            EventDetailView(
                eventId = eventId,
                navController = navController)
        }
    }

}

@Composable
fun Events(
    onEventClick: (Long) -> Unit,
    eventCollection: List<EventCollection>
){
    LazyColumn{
        itemsIndexed(
            items = eventCollection
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

object EventDestination{
    const val EVENT_ROUTE = "events"
    const val EVENT_DETAIL_ROUTE = "event"
    const val EVENT_DETAIL_ID = "eventId"
}

@Preview(showBackground = true)
@Composable
fun PreviewEventScreen(){
    KausanhakTheme {
        EventScreen()
    }
}