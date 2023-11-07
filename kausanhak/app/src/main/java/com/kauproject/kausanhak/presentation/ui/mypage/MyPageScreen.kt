package com.kauproject.kausanhak.presentation.ui.mypage

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import kotlinx.coroutines.launch

const val TAG = "MyPageScreen"
@Composable
fun MyPageScreen(
    navController: NavHostController,
    onLoginScreen: () -> Unit,
    context: Context
){
    val viewModel:MyPageViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember{ SnackbarHostState() }
    val error = stringResource(id = R.string.server_error)

    Scaffold(
        bottomBar = {
            CatchPlanBottomBar(navController = navController, currentRoute = BottomNavItem.Mypage.screenRoute)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(vertical = 10.dp)
                .background(Color.White)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                onClick = {
                    viewModel.getLogOut()
                    onLoginScreen()
                }
            ) {
                Text(text = stringResource(id = R.string.myPage_logout))
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                onClick = {
                    scope.launch {
                        viewModel.deleteUser().collect{ state->
                            when(state){
                                is State.Loading -> {}
                                is State.Success -> { onLoginScreen() }
                                is State.ServerError -> {
                                    snackbarHostState.showSnackbar(
                                        message = error + "${state.code}",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                is State.Error -> { Log.d(TAG, "Error:${state.exception}") }
                            }
                        }
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.myPage_delete))
            }


        }


    }
}
