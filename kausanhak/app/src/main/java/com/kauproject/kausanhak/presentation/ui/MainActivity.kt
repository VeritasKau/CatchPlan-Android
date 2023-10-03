package com.kauproject.kausanhak.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.kauproject.kausanhak.ui.theme.KausanhakTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.CatchPlanApp
import com.kauproject.kausanhak.presentation.ui.login.LoginScreen
import com.kauproject.kausanhak.presentation.ui.login.LoginViewModel
import com.kauproject.kausanhak.ui.theme.CALENDAR
import com.kauproject.kausanhak.ui.theme.EXAMPLE

class MainActivity : ComponentActivity() {
    private val loginViewModel = LoginViewModel(this@MainActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            KausanhakTheme {
                CatchPlanApp(context = this)
            }
        }
    }
}


// 메인뷰
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainScreenView(){
    val navController = rememberNavController()
    val buttonVisible = remember { mutableStateOf(true) }

    // 레이아웃 구성
    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                state = buttonVisible,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 0.2.dp,
                        color = Color.Black
                    )
            )
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .background(Color.White)
            ,
            contentAlignment = Alignment.Center
        ){
            NavigationGraph(navController = navController)
        }
    }

}

// 하단바 구성
@Composable
fun BottomBar(
    navController: NavHostController,
    state: MutableState<Boolean>,
    modifier: Modifier
){
    val screens = listOf(
        BottomNavItem.Calendar,
        BottomNavItem.Ex
    )

    NavigationBar(
        modifier = modifier
            .height(60.dp)
        ,
        containerColor = Color.White
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach{screen ->
            NavigationBarItem(
                modifier = Modifier,
                icon = {
                       Icon(
                           imageVector = screen.icon,
                           contentDescription = null
                       )
                },
                selected = currentRoute == screen.screenRoute,
                onClick = {
                    navController.navigate(screen.screenRoute){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.Gray, selectedIconColor = Color.White
                )
            )
        }

    }

}

// 하단바 아이템 설정
sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val screenRoute: String
){
    object Calendar: BottomNavItem(
        "캘린더",
        Icons.Rounded.DateRange,
        CALENDAR)
    object Ex: BottomNavItem(
        "예제",
        Icons.Rounded.List,
        EXAMPLE)
}

@Composable
fun NavigationGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination =  BottomNavItem.Calendar.screenRoute){
        composable(BottomNavItem.Calendar.screenRoute){
            CalendarScreen()
        }
        composable(BottomNavItem.Ex.screenRoute){
            EventView()
        }
    }
}

