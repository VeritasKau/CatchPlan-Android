package com.kauproject.kausanhak.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kauproject.kausanhak.R

@Composable
fun MyPageScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.myPage_bottomItem))
        
    }
}