package com.kauproject.kausanhak.presentation.ui.mypage.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.data.remote.response.GetUserInfoResponse
import com.kauproject.kausanhak.domain.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val TAG = "ProfileScreen"
@Composable
fun ProfileScreen(
    navController: NavController
){
    val viewModel: ProfileViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val userData = remember{ mutableStateOf<GetUserInfoResponse>(GetUserInfoResponse()) }

    LaunchedEffect(Unit){
        viewModel.initUserData().collect{ state->
            when(state){
                is State.Loading -> { Log.d(TAG, "Loading") }
                is State.Success -> { userData.value = state.data }
                is State.ServerError -> { snackbarHostState.showSnackbar(
                    message = "서버 통신 오류: ${state.code}",
                    duration = SnackbarDuration.Short
                ) }
                is State.Error -> { snackbarHostState.showSnackbar(
                    message = "Error: ${state.exception}",
                    duration = SnackbarDuration.Short
                ) }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.White,
        topBar = { TopBar(
            backPress = { navController.navigateUp() }
        )}
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
            ,
        ) {
            Log.d(TAG, "UserData:${userData.value}")
        }

    }

}

@Composable
private fun TopBar(
    backPress: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .height(50.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.Center
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
                    contentDescription = null,
                    tint = colorResource(id = R.color.purple_main)
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 36.dp)
                ,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier
                    ,
                    text = stringResource(id = R.string.profile_top_bar),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.purple_main)
                )
            }
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
    }
}