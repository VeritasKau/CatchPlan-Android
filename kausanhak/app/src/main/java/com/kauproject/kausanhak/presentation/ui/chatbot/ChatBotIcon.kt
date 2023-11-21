package com.kauproject.kausanhak.presentation.ui.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.Destination
import com.kauproject.kausanhak.presentation.util.clickable

@Composable
fun ChatBotIcon(
    navController: NavController
){
    Column(
        modifier = Modifier
            .wrapContentSize()
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Transparent)
        ,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        Surface(
            modifier = Modifier
                .size(80.dp)
                .clickable { navController.navigate(Destination.CHAT_BOT_ROUTE) }
            ,
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Image(
                modifier = Modifier
                    .padding(15.dp)
                ,
                painter = painterResource(id = R.drawable.ic_app_icon),
                contentDescription = null
            )

        }
    }
}