package com.kauproject.kausanhak.presentation.ui.event

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventCollection
import com.kauproject.kausanhak.domain.model.mockTheaterEvents
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme

private val CardWidth = 170.dp
private val CardPadding = 16.dp

private val gradientWidth
    @Composable
    get() = with(LocalDensity.current){
        (3 * (CardWidth + CardPadding).toPx())
    }

private val colors
    @Composable
    get() = listOf(
        colorResource(id = R.color.rose_4),
        colorResource(id = R.color.lavender_3),
        colorResource(id = R.color.rose_2),
        colorResource(id = R.color.lavender_3),
        colorResource(id = R.color.rose_4)
    )
@Composable
fun EventCollection(
    eventCollection: EventCollection,
    onEventClick: (Int) -> Unit,
    onArrowClick: (Int) -> Unit
){
    val TAG = "EventCollection"

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(56.dp)
                .padding(start = 24.dp)
        ){
            Text(
                text = eventCollection.name,
                color = colorResource(id = R.color.purple_main),
                maxLines = 1,

                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
                    .weight(1f)
            )
            IconButton(
                onClick = {
                    onArrowClick(eventCollection.id)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                    tint = colorResource(id = R.color.purple_main),
                    contentDescription = null
                )
            }
        }
        EventCards(
            events = eventCollection.events,
            onEventClick = onEventClick
        )
    }
}

@Composable
private fun EventCards(
    events: List<Event>,
    onEventClick: (Int) -> Unit
){
    val gradientWidth = with(LocalDensity.current){
        (6 * (CardWidth + CardPadding).toPx())
    }
    val scroll = rememberScrollState(0)
    LazyRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp)
    ){
        itemsIndexed(events){index, event ->
            EventCard(
                modifier = Modifier,
                event = event,
                index = index,
                onEventClick = onEventClick,
                gradientWidth = gradientWidth,
                scroll = scroll.value
            )
        }

    }

}

@Composable
fun EventCard(
    modifier: Modifier,
    event: Event,
    index: Int,
    onEventClick: (Int) -> Unit,
    gradientWidth: Float,
    scroll: Int,
){
    val left = index * with(LocalDensity.current){
        (CardWidth + CardPadding).toPx()
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(
                    width = 170.dp,
                    height = 250.dp
                )
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(5.dp)
                )
            ,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = {
                        onEventClick(event.id)
                    })
            ) {
                Box(
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                ){
                    val gradientOffset = left - (scroll - 3f)
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = colors,
                                    startX = -gradientOffset,
                                    endX = gradientWidth - gradientOffset,
                                    tileMode = TileMode.Mirror
                                )
                            )
                    )
                    EventImage(
                        imageUrl = event.image,
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.BottomCenter)
                        ,
                        contentDescription = null)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .align(Alignment.CenterHorizontally)
                    ,
                    text = event.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 20.dp)
                    ,
                    text = event.place,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                    ,
                    text = event.date,
                    style = MaterialTheme.typography.labelSmall
                )

            }

        }

    }


}

@Composable
fun EventImage(
    imageUrl: String,
    modifier: Modifier,
    contentDescription: String?
){
    Surface(
        color = Color.LightGray,
        shape = CircleShape,
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )

    }

}


