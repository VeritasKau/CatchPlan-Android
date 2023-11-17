package com.kauproject.kausanhak.presentation.anim.lottieanimation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    modifier: Modifier,
    isCompleted: Boolean
    ) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_loading))

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(200.dp)
            ,
            composition = composition,
            iterations = if (isCompleted) 1 else LottieConstants.IterateForever
        )

    }
}

@Composable
fun LottieChatAnimation(
    modifier: Modifier,
    isCompleted: Boolean
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_chat_loading))

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier
                .wrapContentSize()
            ,
            composition = composition,
            iterations = if (isCompleted) 1 else LottieConstants.IterateForever,
            isPlaying = isCompleted
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