package com.kauproject.kausanhak.presentation.ui.upload.uploadstep

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.PromotionEvent
import com.kauproject.kausanhak.presentation.anim.lottieanimation.LottieCongratsAnimation
import com.kauproject.kausanhak.presentation.ui.promotion.PromotionCard
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import com.kauproject.kausanhak.presentation.ui.upload.UpLoadFormViewModel

@Composable
fun Preview(
    modifier: Modifier,
    viewModel: UpLoadFormViewModel
){
    val event = PromotionEvent(
        title = viewModel.title,
        content = viewModel.content,
        startDate = viewModel.startDate,
        endDate = viewModel.endDate,
        image = viewModel.mainImageUri,
        place = viewModel.place
    )

    LottieCongratsAnimation(
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .width(300.dp)
            ,
            text = stringResource(id = R.string.upload_preview_title),
            fontSize = 14.sp,

        )
        Text(
            modifier = Modifier
                .width(300.dp)
            ,
            text = stringResource(id = R.string.upload_preview_complete),
            fontSize = 28.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.purple_main)
        )
        Spacer(modifier = Modifier.padding(vertical = 15.dp))
        PromotionCard(event = event)
        viewModel.onComplete()
    }
}

@Preview(showBackground = true)
@Composable
private fun Exam(){
    val viewModel: UpLoadFormViewModel = hiltViewModel()
    KausanhakTheme {
        Preview(
            modifier = Modifier,
            viewModel = viewModel
        )
    }
}