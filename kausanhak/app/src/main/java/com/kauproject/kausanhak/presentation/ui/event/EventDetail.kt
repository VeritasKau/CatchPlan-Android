package com.kauproject.kausanhak.presentation.ui.event

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventRepo
import com.kauproject.kausanhak.presentation.ui.NetworkImage
import com.kauproject.kausanhak.ui.theme.KausanhakTheme

@Composable
fun EventDetailView(
    eventId: Long,
    navController: NavController
) {
    Log.d("TEST_DETAIL", "$eventId")
    val event = remember(eventId) { EventRepo.getEvent(eventId) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        NetworkImage(
            modifier = Modifier
                .fillMaxSize()
            ,
            url = event.image
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(18.dp)
            ,
            text = event.date
        )
        NetworkImage(
            modifier = Modifier
                .fillMaxSize()
            ,
            url = event.detailImage
        )
    }


}

@Preview(showBackground = true)
@Composable
fun DetailPreview(){
    val mock = Event.mock()
    val navController = rememberNavController()
    KausanhakTheme {
        EventDetailView(
            eventId = 1L,
            navController = navController
        )
    }
}