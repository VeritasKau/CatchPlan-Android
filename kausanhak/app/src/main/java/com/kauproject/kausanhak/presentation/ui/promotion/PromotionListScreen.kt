package com.kauproject.kausanhak.presentation.ui.promotion

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.anim.lottieanimation.LottieLoadingAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PromotionListScreen(
    onEventClick: (Int) -> Unit,
    navController: NavController
) {
    val viewModel: PromotionListViewModel = hiltViewModel()
    val promotion = viewModel.getPromotionList()

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember{ mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        viewModel.fetchEvent()
        viewModel.fetchPromotion()
        viewModel.getPromotionList()
        delay(500)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    Scaffold(
        topBar = { PromotionListTopBar(
            title = stringResource(id = R.string.promotion_title),
            backPress = { navController.navigateUp() }
        ) },
        containerColor = Color.White
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .pullRefresh(refreshState)
            ,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
        ){
            itemsIndexed(promotion){ index, event ->
                if(index == 0){
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
                }
                PromotionCard(
                    modifier = Modifier.fillMaxWidth(),
                    event = event,
                    onEventClicked = onEventClick)
            }
        }

    }
}

@Composable
private fun PromotionListTopBar(
    title: String,
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
    ) {
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
                tint = Color.Black
            )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 36.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                ,
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

    }

}
