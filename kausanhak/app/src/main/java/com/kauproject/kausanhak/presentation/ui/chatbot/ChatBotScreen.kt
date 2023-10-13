package com.kauproject.kausanhak.presentation.ui.chatbot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kauproject.kausanhak.R

@Composable
fun ChatBotScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.chatBot_bottomItem))

    }
}