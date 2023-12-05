package com.kauproject.kausanhak.presentation.ui

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.data.remote.service.info.GetUserInfoService
import com.kauproject.kausanhak.data.remote.service.login.CheckMemberService
import com.kauproject.kausanhak.data.remote.service.login.SignInService
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.PurchaseHelper
import com.kauproject.kausanhak.presentation.anim.pageanimation.noAnimatedComposable
import com.kauproject.kausanhak.presentation.anim.pageanimation.verticallyAnimatedComposable
import com.kauproject.kausanhak.presentation.ui.login.LoginScreen
import com.kauproject.kausanhak.presentation.ui.setting.SettingScreen

enum class CatchPlanScreen(@StringRes val title: Int){
    Login(title = R.string.choose_login),
    Main(title = R.string.choose_main),
    Setting(title = R.string.choose_setting)
}

const val TAG = "CatchPlanScreen"

@Composable
fun CatchPlanApp(
    userDataRepository: UserDataRepository,
    signInService: SignInService,
    checkMemberService: CheckMemberService,
    getUserInfoService: GetUserInfoService,
    purchaseHelper: PurchaseHelper,
    context: Context
){
    val navController = rememberNavController()
    var isMember by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        isMember = userDataRepository.getUserData().token != ""
        Log.d("Location Test:", userDataRepository.getUserData().location)
        Log.d("USER:",userDataRepository.getUserData().name)
    }

    val startScreen = if(isMember) CatchPlanScreen.Main.name else CatchPlanScreen.Login.name

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = startScreen
    ){
        noAnimatedComposable(route = CatchPlanScreen.Login.name){
            LoginScreen(
                onLoginButtonClicked = { it->
                    navController.navigate(it){
                        popUpTo(CatchPlanScreen.Login.name){
                            inclusive = true
                        }
                    } },
                userDataRepository = userDataRepository,
                signInService = signInService,
                checkMemberService = checkMemberService,
                getUserInfoService = getUserInfoService,
                context = context
            )
        }
        noAnimatedComposable(route = CatchPlanScreen.Setting.name){
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
                },
                purchaseHelper = purchaseHelper
            )
        }

    }

}
