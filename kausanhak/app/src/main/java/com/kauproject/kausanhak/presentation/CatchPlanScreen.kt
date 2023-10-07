package com.kauproject.kausanhak.presentation

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.pageanimation.horizontallyAnimatedComposable
import com.kauproject.kausanhak.presentation.pageanimation.verticallyAnimatedComposable
import com.kauproject.kausanhak.presentation.ui.MainScreen
import com.kauproject.kausanhak.presentation.ui.login.LoginScreen
import com.kauproject.kausanhak.presentation.ui.setting.FavoriteSettingScreen
import com.kauproject.kausanhak.presentation.ui.setting.MbtiSettingScreen
import com.kauproject.kausanhak.presentation.ui.setting.SettingScreen

enum class CatchPlanScreen(@StringRes val title: Int){
    Login(title = R.string.choose_login),
    Main(title = R.string.choose_main),
    Setting(title = R.string.choose_setting)
}

@Composable
fun CatchPlanApp(
    navController: NavHostController = rememberNavController(),
    context: Context
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CatchPlanScreen.valueOf(
        backStackEntry?.destination?.route ?: CatchPlanScreen.Login.name
    )

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
        verticallyAnimatedComposable(route = CatchPlanScreen.Main.name){
            MainScreen()
        }

    }

}
