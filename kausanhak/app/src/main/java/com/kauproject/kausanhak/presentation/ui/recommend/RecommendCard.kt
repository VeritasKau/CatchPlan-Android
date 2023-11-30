package com.kauproject.kausanhak.presentation.ui.recommend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.presentation.util.clickable

@Composable
fun RecommendCard(
    event: Event,
    onEventClicked: (Int) -> Unit,
    modifier: Modifier
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = modifier
                .shadow(shape = RoundedCornerShape(10.dp), elevation = 5.dp)
                .width(400.dp)
                .height(500.dp)
                .clickable { onEventClicked(event.id) }
            ,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ){
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                RecommendImage(
                    imageUrl = event.image,
                    modifier = Modifier.fillMaxSize()
                )
                RecommendText(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 100.dp)
                        .padding(horizontal = 16.dp)
                    ,
                    text = event.name,
                    fontSize = 42.sp,)

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 16.dp)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    RecommendText(
                        modifier = Modifier
                        ,
                        text = event.date,
                        fontSize = 24.sp)
                    Spacer(modifier = Modifier.padding(vertical = 3.dp))
                    Row {
                        Icon(
                            modifier = Modifier.padding(end = 5.dp),
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color.White)
                        RecommendText(
                            modifier = Modifier,
                            text = event.place,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

    }

}

@Composable
private fun RecommendImage(
    modifier: Modifier,
    imageUrl: String
){
    AsyncImage(
        modifier = modifier.fillMaxSize(),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
private fun RecommendText(
    modifier: Modifier,
    text: String,
    fontSize: TextUnit,
){
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight.ExtraBold,
        fontSize = fontSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = Color.White,
        style = TextStyle(shadow = Shadow(
            color = Color.Black,
            blurRadius = 20f
        )
        )
    )

}
