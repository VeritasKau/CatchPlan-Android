package com.kauproject.kausanhak.presentation.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.data.remote.service.login.SignInService
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.util.Constants.KAKAO
import com.kauproject.kausanhak.presentation.util.Constants.NAVER
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import kotlinx.coroutines.delay


@Composable
fun LoginScreen(
    onLoginButtonClicked: () -> Unit,
    userDataRepository: UserDataRepository,
    signInService: SignInService
) {
    val context = LocalContext.current
    val loginViewModel = LoginViewModel(context, userDataRepository, signInService)
    val userDataState by loginViewModel.userData.collectAsState()

    if(userDataState.userId != ""){
        onLoginButtonClicked()
    }

    LaunchedEffect(Unit){
        Log.d("TEST login", "${userDataRepository.getUserData().userNum}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_app_icon),
            contentDescription = "app icon"
        )

        Text(
            text = "모든 행사를 한번에,",
            color = Color.DarkGray
        )
        Text(
            modifier = Modifier,
            text = "캐치플랜",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.purple_title)
        )

        Spacer(Modifier.size(50.dp))

        Button(
            modifier = Modifier
                .width(310.dp)
                .height(51.dp)
            ,
            onClick = { loginViewModel.startLogin(KAKAO) },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.kakao_login)),
            shape = RoundedCornerShape(8.dp)
        ) {
            LoginBar(id = R.drawable.ic_login_kakao, platform = KAKAO)
        }

        Spacer(
            Modifier.size(15.dp)
        )

        Button(
            modifier = Modifier
                .width(310.dp)
                .height(51.dp)
            ,
            onClick = { loginViewModel.startLogin(NAVER) },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.naver_login)),
            shape = RoundedCornerShape(8.dp)
        ) {
            LoginBar(id = R.drawable.ic_login_naver, platform = "네이버")
        }

    }

}

@Composable
fun LoginBar(id: Int, platform: String){
    val title = platform + "로 시작하기"
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = id),
            contentDescription = platform
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 6.dp)
            ,
            text = title,
            color = if(platform == KAKAO) Color.Black else Color.White,
            fontWeight = FontWeight.Bold
        )
    }

}



