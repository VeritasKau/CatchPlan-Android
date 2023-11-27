package com.kauproject.kausanhak.presentation.ui.promotion

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.PromotionEvent
import com.kauproject.kausanhak.domain.model.mockTheaterEvents
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme

@Composable
fun PromotionCard(
    event: Event
){
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .shadow(shape = RoundedCornerShape(10.dp), elevation = 5.dp)
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(event.url)
                .size(coil.size.Size.ORIGINAL)
                .build()
        )

        AsyncImage(
            modifier = Modifier
                .weight(0.7f)
            ,
            model = ImageRequest.Builder(LocalContext.current)
                .data(event.url)
                .size(coil.size.Size.ORIGINAL)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .weight(0.3f)
                .padding(horizontal = 10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = event.name,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(vertical = 1.dp))
            Text(
                text = event.place
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Text(
                text = event.date,
                fontSize = 12.sp
            )

        }

    }

}

@Composable
fun PreviewPromotionCard(
    event: PromotionEvent
){
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(400.dp)
            .shadow(shape = RoundedCornerShape(10.dp), elevation = 5.dp)
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Image(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxSize()
            ,
            painter = rememberAsyncImagePainter(event.image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier
                .weight(0.3f)
                .padding(horizontal = 10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(vertical = 1.dp))
            Text(
                text = event.place
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Text(
                text = "${event.startDate}~${event.endDate}"
            )

        }

    }

}


@Preview(showBackground = true)
@Composable
private fun PreviewPromotionCard2(){
    val event = mockTheaterEvents[0]
    KausanhakTheme {
        PromotionCard(event = event)
    }
}