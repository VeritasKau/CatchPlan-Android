package com.kauproject.kausanhak.presentation.ui.mypage

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import kotlinx.coroutines.launch

@Composable
fun MyPageScreen(
    navController: NavHostController,
    onLoginScreen: () -> Unit,
    context: Context
){
    val viewModel:MyPageViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            CatchPlanBottomBar(navController = navController, currentRoute = BottomNavItem.Mypage.screenRoute)
        }
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
                        viewModel.deleteUser(context)
                        onLoginScreen()
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.myPage_delete))
            }


        }


    }
}
