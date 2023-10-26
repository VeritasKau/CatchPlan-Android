package com.kauproject.kausanhak.presentation.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.data.remote.service.login.SignInService
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.pageanimation.verticallyAnimatedComposable
import com.kauproject.kausanhak.presentation.ui.login.LoginScreen
import com.kauproject.kausanhak.presentation.ui.setting.SettingScreen

enum class CatchPlanScreen(@StringRes val title: Int){
    Login(title = R.string.choose_login),
    Main(title = R.string.choose_main),
    Setting(title = R.string.choose_setting)
}

@Composable
fun CatchPlanApp(
    userDataRepository: UserDataRepository,
    signInService: SignInService
){
    val navController = rememberNavController()
    val isMember = remember{ mutableStateOf(false) }

    LaunchedEffect(Unit){
        isMember.value = userDataRepository.getUserData().userNum != null
    }

    val startScreen = if(isMember.value) CatchPlanScreen.Main.name else CatchPlanScreen.Login.name

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = startScreen
    ){
        composable(route = CatchPlanScreen.Login.name){
            LoginScreen(
                onLoginButtonClicked = {
                    navController.navigate(CatchPlanScreen.Setting.name){
                        popUpTo(CatchPlanScreen.Login.name){
                            inclusive = true
                        }
                    } },
                userDataRepository = userDataRepository,
                signInService = signInService
            )
        }
        composable(route = CatchPlanScreen.Setting.name){
            SettingScreen(
                onComplete = {
                    navController.navigate(CatchPlanScreen.Main.name){
                        popUpTo(CatchPlanScreen.Setting.name){
                            inclusive = true
                        }
                    }
                }
            )
        }
        verticallyAnimatedComposable(route = CatchPlanScreen.Main.name) {
            MainScreen()
        }

    }

}
