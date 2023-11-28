package com.kauproject.kausanhak.presentation.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.presentation.ui.BottomNavItem
import com.kauproject.kausanhak.presentation.ui.CatchPlanBottomBar
import com.kauproject.kausanhak.presentation.util.clickable

const val TAG = "MyPageScreen"
@Composable
fun MyPageScreen(
    navController: NavHostController,
    onLoginScreen: () -> Unit,
    onScrapScreen: () -> Unit,
    onUpLoadScreen: () -> Unit,
){
    val viewModel:MyPageViewModel = hiltViewModel()
    val snackbarHostState = remember{ SnackbarHostState() }
    val error = stringResource(id = R.string.server_error)
    var showDialog by remember { mutableStateOf(false) }
    var isDelete by remember { mutableStateOf(false) }

    if(showDialog){
        DeleteDialog(
            showDialog = { showDialog = it },
            isDelete = { isDelete = it }
        )
    }

    if(isDelete){
        LaunchedEffect(Unit){
            viewModel.deleteUser().collect { state ->
                when (state) {
                    is State.Loading -> {}
                    is State.Success -> {
                        onLoginScreen()
                    }
                    is State.ServerError -> {
                        snackbarHostState.showSnackbar(
                            message = error + "${state.code}",
                            duration = SnackbarDuration.Short
                        )
                    }
                    is State.Error -> {
                        snackbarHostState.showSnackbar(
                            message = "ERROR: ${state.exception}",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            CatchPlanBottomBar(navController = navController, currentRoute = BottomNavItem.Mypage.screenRoute)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.White,
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(Color.White)
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .clickable { onScrapScreen() }
                ,
                text = stringResource(id = R.string.myPage_scrap),
                fontSize = 18.sp
            )
            HorizontalDivider()
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .clickable { onUpLoadScreen() }
                ,
                text = stringResource(id = R.string.myPage_upload),
                fontSize = 18.sp
            )
            HorizontalDivider()
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .clickable {
                        viewModel.getLogOut()
                        onLoginScreen()
                    },
                text = stringResource(id = R.string.myPage_logout),
                fontSize = 18.sp
            )
            HorizontalDivider()
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .clickable { showDialog = true }
                ,
                text = stringResource(id = R.string.myPage_delete),
                fontSize = 18.sp
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun DeleteDialog(
    showDialog: (Boolean) -> Unit,
    isDelete: (Boolean) -> Unit
){
    Dialog(onDismissRequest = { showDialog(false) }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
            ,
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ){
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                    ,
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "회원탈퇴",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Text(text = "회원 탈퇴 시 사용자 계정의 모든 정보가 삭제 됩니다.", fontSize = 12.sp)
                Text(text = "정말로 탈퇴 하시겠습니까?", fontSize = 12.sp)
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { showDialog(false) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray
                        )
                    ) {
                        Text(text = "취소", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Button(
                        onClick = {
                            isDelete(true)
                            showDialog(false) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.red_0)
                        )
                    ) {
                        Text(text = "회원탈퇴", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
