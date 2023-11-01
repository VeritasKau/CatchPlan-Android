package com.kauproject.kausanhak.presentation.ui

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.data.remote.service.login.SignInService
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.pageanimation.verticallyAnimatedComposable
import com.kauproject.kausanhak.presentation.ui.login.LoginScreen
import com.kauproject.kausanhak.presentation.ui.setting.SettingScreen
import kotlin.contracts.contract

enum class CatchPlanScreen(@StringRes val title: Int){
    Login(title = R.string.choose_login),
    Main(title = R.string.choose_main),
    Setting(title = R.string.choose_setting)
}

const val TAG = "CatchPlanScreen"

@Composable
fun CatchPlanApp(
    userDataRepository: UserDataRepository,
    signInService: SignInService
){
    val navController = rememberNavController()
    val isMember = remember { mutableStateOf(false) }
    val isDelete = remember{ mutableStateOf(false) }

    LaunchedEffect(key1 = userDataRepository.getTokenData().value){
        isMember.value = userDataRepository.getUserData().token != "" && userDataRepository.getUserData().num != ""
        isDelete.value = userDataRepository.getUserData().firstFavorite == ""
    }

    val startScreen = if(isMember.value) CatchPlanScreen.Main.name else CatchPlanScreen.Login.name
    val loginScreen = if(isDelete.value) CatchPlanScreen.Setting.name else CatchPlanScreen.Main.name

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = startScreen
    ){
        composable(route = CatchPlanScreen.Login.name){
            LoginScreen(
                onLoginButtonClicked = {
                    navController.navigate(loginScreen){
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
            MainScreen(
                onLoginScreen = {
                    navController.navigate(CatchPlanScreen.Login.name){
                        popUpTo(CatchPlanScreen.Main.name){
                            inclusive = true
                        }
                    }
                }
            )
        }

    }

}
