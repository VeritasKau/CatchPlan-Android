package com.kauproject.kausanhak.presentation.anim.lottieanimation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kauproject.kausanhak.R

@Composable
fun LottieLoadingAnimation(
    modifier: Modifier
    ) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_loading))

    Column(
        modifier = modifier
            .background(Color.Transparent)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(200.dp)
            ,
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true
        )
    }
}

@Composable
fun LottieChatAnimation(
    modifier: Modifier,
    isCompleted: Boolean
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_chat_loading))

    if(!isCompleted){
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = modifier
                ,
                composition = composition,
                iterations = LottieConstants.IterateForever,
                isPlaying = true
            )
        }
    }
}

@Composable
fun LottieUpLoadAnimation(
    modifier: Modifier
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_upload_welcom))
    Box(modifier = modifier){
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true
        )
    }

}

@Composable
fun LottieBackgroundAnimation(
    modifier: Modifier
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_background))
    Box(modifier = modifier){
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true
        )
    }
}
@Composable
fun LottieDotAnimation(
    modifier: Modifier,
    isCompleted: Boolean
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_dot_loading))

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(300.dp)
            ,
            composition = composition,
            iterations = if (isCompleted) 1 else LottieConstants.IterateForever,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun LottieCongratsAnimation(
    modifier: Modifier
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_congrats))

    Box(modifier = modifier){
        LottieAnimation(
            composition = composition,
            iterations = 1
        )
    }
}
