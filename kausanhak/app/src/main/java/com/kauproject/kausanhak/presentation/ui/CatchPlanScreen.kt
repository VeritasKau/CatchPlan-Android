package com.kauproject.kausanhak.presentation.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.pageanimation.verticallyAnimatedComposable
import com.kauproject.kausanhak.presentation.ui.event.EventDestination
import com.kauproject.kausanhak.presentation.ui.event.EventDetailScreen
import com.kauproject.kausanhak.presentation.ui.login.LoginScreen
import com.kauproject.kausanhak.presentation.ui.setting.SettingScreen

enum class CatchPlanScreen(@StringRes val title: Int){
    Login(title = R.string.choose_login),
    Main(title = R.string.choose_main),
    Setting(title = R.string.choose_setting)
}

@Composable
fun CatchPlanApp(){
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = CatchPlanScreen.Login.name
    ){
        composable(route = CatchPlanScreen.Login.name){
            LoginScreen(
                onLoginButtonClicked = {
                    navController.navigate(CatchPlanScreen.Setting.name){
                        popUpTo(CatchPlanScreen.Login.name){
                            inclusive = true
                        }
                    } },
                context = context
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
