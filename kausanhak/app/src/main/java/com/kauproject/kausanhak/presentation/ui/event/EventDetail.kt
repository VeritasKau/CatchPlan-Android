package com.kauproject.kausanhak.presentation.ui.event

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.model.EventRepo
import com.kauproject.kausanhak.ui.theme.KausanhakTheme
import kotlin.math.max
import kotlin.math.min
import androidx.compose.ui.unit.lerp as lerp

private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinImageOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

private val colors
    @Composable
    get() = listOf(
        colorResource(id = R.color.lavender_3),
        colorResource(id = R.color.rose_4)
    )

@Composable
fun EventDetailScreen(
    eventId: Long,
    navController: NavController
){
    val event = remember(eventId) { EventRepo.getEvent(eventId) }
    Box(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()
    ){
        val scroll = rememberScrollState(0)
        Header()
        Body(scroll = scroll, event = event)
        Title(event){ scroll.value }
        Image(event) { scroll.value }
    }


}

@Composable
private fun Header(){
    Spacer(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(colors))
    )
}

@Composable
private fun Body(
    scroll: ScrollState,
    event: Event
){
    Column(
        modifier = Modifier
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier
                .verticalScroll(scroll)
                .background(Color.Transparent)
        ) {
            Spacer(Modifier.height(GradientScroll))
            Surface(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                ) {
                    Spacer(modifier = Modifier.height(ImageOverlap))
                    Spacer(modifier = Modifier.height(TitleHeight))

                    Spacer(modifier = Modifier.height(16.dp))
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(event.detailImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.sample),
                        contentScale = ContentScale.Crop
                    )
                }

            }




        }
    }

}

@Composable
private fun Title(event: Event, scrollProvider: () -> Int){
    val maxOffset = with(LocalDensity.current){ MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current){ MinTitleOffset.toPx() }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(Color.White)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = event.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = HzPadding
        )
        Text(
            text = event.date,
            fontSize = 20.sp,
            modifier = HzPadding
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

    }

}

@Composable
private fun Image(
    event: Event,
    scrollProvider: () -> Int
){
    val collapseRange = with(LocalDensity.current){ (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ){
        Surface(
            color = Color.LightGray,
            shape = CircleShape,
            modifier = Modifier
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(event.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.sample),
                contentScale = ContentScale.Crop
            )

        }

    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Layout(
        modifier = modifier,
        content = content
    ){ measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()
        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2,
            constraints.maxWidth - imageWidth,
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ){
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewEventDetailScreen(){
    val navController = rememberNavController()
    KausanhakTheme {
        EventDetailScreen(eventId = 0L, navController = navController)
    }
}