package com.kauproject.kausanhak.presentation.ui.promotion

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.presentation.anim.lottieanimation.LottieLoadingAnimation
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.ui.chatbot.ChatBotIcon
import com.kauproject.kausanhak.presentation.ui.promotion.place.PlaceEventCards
import com.kauproject.kausanhak.presentation.ui.promotion.favorite.FavoriteEventCards
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PromotionScreen(
    navController: NavHostController,
    onPromotionClicked: (Int) -> Unit,
    onPromotionArrowClicked: () -> Unit,
    onPlaceClicked: (Int) -> Unit,
    onFavoriteClicked: (Int) -> Unit,
    onPlaceArrowClicked: () -> Unit,
    onFavoriteArrowClicked: () -> Unit,
) {
    val viewModel: PromotionViewModel = hiltViewModel()
    val promotion = viewModel.promotion.collectAsState()
    val placeEvent = viewModel.placeEvent.collectAsState()
    val favoriteEvent = viewModel.favorite.collectAsState()
    val location = viewModel.location.collectAsState()

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember{ mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        viewModel.fetchEvent()
        viewModel.fetchRecommendEvent()
        delay(500)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
        ,
        bottomBar = {
            CatchPlanBottomBar(navController = navController, currentRoute = BottomNavItem.Promotion.screenRoute)
        },
        containerColor = Color.Transparent,

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(colorResource(id = R.color.gray_1))
                .pullRefresh(refreshState)
            ,
        ) {
            items(count = 1){
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(
                            if (refreshing) {
                                50.dp
                            } else {
                                lerp(0.dp, 50.dp, refreshState.progress.coerceIn(0f..1f))
                            }
                        )
                ){
                    if(refreshing){
                        LottieLoadingAnimation(
                            modifier = Modifier
                                .align(Alignment.TopCenter)

                        )
                    }
                }
                PromotionEventCards(
                    promotion = promotion.value,
                    onPromotionClicked = onPromotionClicked,
                    onPromotionArrowClicked = onPromotionArrowClicked
                )
                Spacer(modifier = Modifier.padding(vertical = 6.dp))
                if(location.value != "" && placeEvent.value.isNotEmpty()){
                    PlaceEventCards(
                        placeEvent = placeEvent.value,
                        onPlaceClicked = onPlaceClicked,
                        onPlaceArrowClicked = onPlaceArrowClicked
                    )
                    Spacer(modifier = Modifier.padding(vertical = 6.dp))
                }
                FavoriteEventCards(
                    favoriteList = favoriteEvent.value,
                    onFavoriteClicked = onFavoriteClicked,
                    onFavoriteArrowClicked = onFavoriteArrowClicked
                )
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

@Composable
private fun PromotionEventCards(
    promotion: List<Event>,
    onPromotionClicked: (Int) -> Unit,
    onPromotionArrowClicked: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 16.dp)
        ,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
                    .padding(start = 16.dp)
                    .weight(1f)
                ,
                text = stringResource(id = R.string.promotion_title),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            IconButton(
                modifier = Modifier
                ,
                onClick = onPromotionArrowClicked
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
            itemsIndexed(promotion){ _, item ->
                PromotionCard(
                    modifier = Modifier.width(200.dp),
                    event = item,
                    onEventClicked = onPromotionClicked)
            }
        }
    }
    
}