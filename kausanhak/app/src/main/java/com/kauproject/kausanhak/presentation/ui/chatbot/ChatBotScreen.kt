package com.kauproject.kausanhak.presentation.ui.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme

@Composable
fun ChatBotScreen(
    navController: NavHostController
){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
        ,
        topBar = {
            chatTopBar(
            backPress = { navController.navigateUp() }
        ) },
        bottomBar = {
            InputChat()
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(colorResource(id = R.color.lavender_4))
        ) {

        }

    }
}

@Composable
private fun chatTopBar(
    backPress: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .height(50.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                ),
            onClick = backPress
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.detail_back),
                tint = colorResource(id = R.color.purple_main)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 36.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ChatBot",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.purple_main),
                fontSize = 24.sp
            )
        }
    }
}

@Composable
private fun InputChat(){
    var textFieldState by remember{ mutableStateOf("") }
    Column(
        modifier = Modifier
            .height(60.dp)
        ,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier
                    .padding(vertical = 7.dp)
                    .padding(start = 10.dp, end = 100.dp)
                ,
                value = textFieldState,
                onValueChange = {},
                shape = RoundedCornerShape(5.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.gray_1),
                    unfocusedContainerColor = colorResource(id = R.color.gray_1),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            IconButton(
                modifier = Modifier
                    .size(30.dp)
                ,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Send,
                    contentDescription = null)
            }
        }
    }

}
@Composable
private fun chatBubble(
    content: String,
    isMe: Boolean
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if(isMe) Arrangement.End else Arrangement.Start
    ){

    }

}

@Preview(showBackground = true)
@Composable
private fun Preview(){
    val navController = rememberNavController()
    KausanhakTheme {
        ChatBotScreen(navController = navController)
    }
}
