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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import com.kauproject.kausanhak.presentation.util.clickable

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
            LazyColumn(
                modifier = Modifier
            ){
                item{
                    chatBubble(content = stringResource(id = R.string.chatBot_base_chat), isMe = false)
                }
            }

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
                fontSize = 20.sp
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
            .background(Color.White)
        ,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .padding(vertical = 7.dp)
                    .padding(start = 10.dp)
                    .weight(0.85f)
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
            Image(
                modifier = Modifier
                    .size(45.dp)
                    .weight(0.15f)
                    .padding(5.dp)
                    .clickable { }
                    .wrapContentSize()
                ,
                painter = painterResource(id = R.drawable.ic_send),
                contentDescription = null
            )
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
            .wrapContentHeight()
            .padding(vertical = 10.dp)
            .padding(start = 10.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if(isMe) Arrangement.End else Arrangement.Start
    ){
        if(isMe){
            
        }else{
            Surface(
                modifier = Modifier
                    .size(40.dp)
                ,
                shape = CircleShape
            ) {
                Image(
                    modifier = Modifier
                        .padding(5.dp)
                    ,
                    painter = painterResource(id = R.drawable.ic_app_icon),
                    contentDescription = null)
            }
            Column(
                modifier = Modifier
                    .padding(start = 5.dp)
                ,
            ) {
                Text(
                    modifier = Modifier
                    ,
                    text = stringResource(id = R.string.chatBot_name),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.purple_main)
                )
                Text(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(vertical = 7.dp, horizontal = 5.dp),
                    text = content,

                )
            }
        }

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
