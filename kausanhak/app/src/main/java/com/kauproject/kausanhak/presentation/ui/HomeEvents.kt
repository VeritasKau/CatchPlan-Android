package com.kauproject.kausanhak.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.ui.theme.KausanhakTheme

@Composable
fun HomeEvents(
    modifier: Modifier = Modifier,
    events: List<Event>,
    selectEvent: (Int) -> Unit,
){
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        events.forEach { event->
            key(event.id){
                HomeEvent(
                    event = event,
                    selectEvent = selectEvent
                )
            }
        }

    }

}

@Composable
private fun HomeEvent(
    modifier: Modifier = Modifier,
    event: Event,
    selectEvent: (Int) -> Unit = {},
){
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .clickable(
                onClick = { selectEvent(event.id) }
            ),
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

@Composable
fun EventView(){
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        itemsIndexed(
            items = getMockList(),
            key = {index, item ->
                item.id
            }
        ){index, item ->
            HomeEvent(event = item)
        }
    }

}

@Composable
@Preview(name = "Event Light Theme")
private fun HomeEventPreviewLight(){
    KausanhakTheme(darkTheme = false) {
        HomeEvent(event = Event.mock())
    }
}

@Composable
@Preview(name = "Event Dark Theme")
private fun HomeEventPreviewDark(){
    KausanhakTheme(darkTheme = true) {
        HomeEvent(event = Event.mock())
    }
}

private fun getMockList() = listOf<Event>(
    Event(0, "블랙핑크", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(1, "A", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(2, "B", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(3, "C", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(4, "D", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(5, "E", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(6, "F", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(7, "G", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(8, "H", "올림픽홀", "2023-09-23", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804")
)