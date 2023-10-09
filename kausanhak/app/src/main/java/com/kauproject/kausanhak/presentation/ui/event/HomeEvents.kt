package com.kauproject.kausanhak.presentation.ui.event

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventRepo
import com.kauproject.kausanhak.presentation.ui.NetworkImage
import com.kauproject.kausanhak.presentation.ui.event.EventDestination.EVENT_DETAIL_ID
import com.kauproject.kausanhak.ui.theme.KausanhakTheme

@Composable
fun EventView(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = EventDestination.EVENT_ROUTE){

        composable(EventDestination.EVENT_ROUTE){
            EventsView(onDetailClick = { id: Long ->
                navController.navigate("${EventDestination.EVENT_DETAIL_ROUTE}/$id")
            })
        }

        composable(
            route = "${EventDestination.EVENT_DETAIL_ROUTE}/{$EVENT_DETAIL_ID}",
            arguments = listOf(navArgument(EVENT_DETAIL_ID){
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
fun EventsView(
    onDetailClick: (Long) -> Unit
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ){
        itemsIndexed(
            items = EventRepo.getMockEventList()
        ){_, item ->
            HomeEventScreen(
                event = item,
                selectEvent = onDetailClick
            )
        }
    }

}

@Composable
private fun HomeEventScreen(
    modifier: Modifier = Modifier,
    event: Event,
    selectEvent: (Long) -> Unit = {},
){
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .clickable(
                onClick = {
                    selectEvent(event.id)
                }
            )
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
        ,
        color = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
        ) {
            NetworkImage(
                modifier = Modifier
                    .width(180.dp)
                ,
                url = event.image
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier
                    ,
                    text = event.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                    ,
                    text = event.place,
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                    ,
                    text = event.date
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

@Composable
@Preview(name = "Event Light Theme")
private fun HomeEventPreviewLight(){
    KausanhakTheme(darkTheme = false) {
        HomeEventScreen(event = Event.mock())
    }
}

@Composable
@Preview(name = "Event Dark Theme")
private fun HomeEventPreviewDark(){
    KausanhakTheme(darkTheme = true) {
        HomeEventScreen(event = Event.mock())
    }
}