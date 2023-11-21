package com.kauproject.kausanhak.presentation.ui.promotion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.ui.chatbot.ChatBotIcon

@Composable
fun PromotionScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
        ,
        bottomBar = {
            CatchPlanBottomBar(navController = navController, currentRoute = BottomNavItem.Promotion.screenRoute)
        },
        containerColor = Color.Transparent,

    ) { paddingValues ->
        Column(
          modifier = Modifier
                .padding(paddingValues)

        ) {

            ChatBotIcon(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ex(){
    Scaffold(
        modifier = Modifier
        ,
        containerColor = Color.Transparent,

        ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Text(
                text = "이런행사는 어때요?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }

}