package com.kauproject.kausanhak.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.kauproject.kausanhak.presentation.ui.calendar.CalendarScreen
import com.kauproject.kausanhak.presentation.ui.chatbot.ChatBotScreen
import com.kauproject.kausanhak.presentation.ui.event.EventScreen
import com.kauproject.kausanhak.presentation.ui.favorite.FavoriteScreen
import com.kauproject.kausanhak.presentation.ui.login.LoginViewModel
import com.kauproject.kausanhak.presentation.ui.mypage.MyPageScreen
import com.kauproject.kausanhak.ui.theme.CALENDAR
import com.kauproject.kausanhak.ui.theme.CHATBOT
import com.kauproject.kausanhak.ui.theme.EVENT
import com.kauproject.kausanhak.ui.theme.FAVORITE
import com.kauproject.kausanhak.ui.theme.KausanhakTheme
import com.kauproject.kausanhak.ui.theme.MYPAGE

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
fun MainScreen(){
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
                        width = 0.1.dp,
                        color = Color.Black,
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
        BottomNavItem.Event,
        BottomNavItem.Favorite,
        BottomNavItem.Chatbot,
        BottomNavItem.Mypage
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
                modifier = Modifier
                ,
                icon = {
                    screen.icon?.let {
                        Image(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                            ,
                            painter = painterResource(id = it),
                            contentDescription = null
                        )
                    }
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
    @StringRes val title: Int,
    @IntegerRes val icon: Int?,
    val screenRoute: String
){
    object Calendar: BottomNavItem(
        R.string.calendar_bottomItem,
        R.drawable.ic_calendar.toInt(),
        CALENDAR)
    object Event: BottomNavItem(
        R.string.event_bottomItem,
        R.drawable.ic_event.toInt(),
        EVENT)
    object Favorite: BottomNavItem(
        R.string.favorite_bottomItem,
        R.drawable.ic_favorite.toInt(),
        FAVORITE
    )
    object Chatbot: BottomNavItem(
        R.string.chatBot_bottomItem,
        R.drawable.ic_chat.toInt(),
        CHATBOT
    )
    object Mypage: BottomNavItem(
        R.string.myPage_bottomItem,
        R.drawable.ic_mypage.toInt(),
        MYPAGE
    )
}



@Composable
fun NavigationGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination =  BottomNavItem.Calendar.screenRoute){
        composable(BottomNavItem.Calendar.screenRoute){
            CalendarScreen()
        }
        composable(BottomNavItem.Event.screenRoute){
            EventScreen()
        }
        composable(BottomNavItem.Favorite.screenRoute){
            FavoriteScreen()
        }
        composable(BottomNavItem.Chatbot.screenRoute){
            ChatBotScreen()
        }
        composable(BottomNavItem.Mypage.screenRoute){
            MyPageScreen()
        }
    }
}

