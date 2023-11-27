package com.kauproject.kausanhak.presentation.ui.promotion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.ui.chatbot.ChatBotIcon
import com.kauproject.kausanhak.presentation.ui.event.EventCards

@Composable
fun PromotionScreen(
    navController: NavHostController
) {
    val viewModel: PromotionViewModel = hiltViewModel()
    val promotion = viewModel.promotion.collectAsState()
    val placeEvent = viewModel.placeEvent.collectAsState()
    val nick = viewModel.userNick.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
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
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                            .weight(1f)
                        ,
                        text = "캐치플랜이 추천하는 이벤트",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    IconButton(
                        modifier = Modifier
                        ,
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            modifier = Modifier
                            ,
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null)
                    }
                }
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ){
                    itemsIndexed(promotion.value){ _, item ->
                        PromotionCard(event = item)
                    }
                }
            }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                            .weight(1f)
                        ,
                        text = "주변에 열리는 이벤트",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    IconButton(
                        modifier = Modifier
                        ,
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            modifier = Modifier
                            ,
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null)
                    }
                }
                EventCards(events = placeEvent.value, onEventClick = {})
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }
        }
        Box(
            modifier = Modifier
                .padding(bottom = 60.dp)
        ){
            ChatBotIcon(navController = navController)
        }
    }
}

