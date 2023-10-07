package com.kauproject.kausanhak.presentation.ui.setting

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.pageanimation.horizontallyAnimatedComposable

enum class SettingRoute(@StringRes val title: Int){
    mbti(title = R.string.choose_mbti),
    Favorite(title = R.string.choose_favorite)
}

@Composable
fun SettingScreen(
    navController: NavHostController = rememberNavController(),
    onComplete: () -> Unit
){
    NavHost(
        navController = navController, 
        startDestination = SettingRoute.mbti.name){
        horizontallyAnimatedComposable(route = SettingRoute.mbti.name){
            MbtiSettingScreen(
                onNextButtonClick = {
                    navController.navigate(SettingRoute.Favorite.name){
                        popUpTo(SettingRoute.mbti.name){
                            inclusive = true
                        }
                    }
                }
            )
        }
        horizontallyAnimatedComposable(route = SettingRoute.Favorite.name){
            FavoriteSettingScreen(
                onCompleteButtonClick = onComplete
            )
        }
    }
    
}