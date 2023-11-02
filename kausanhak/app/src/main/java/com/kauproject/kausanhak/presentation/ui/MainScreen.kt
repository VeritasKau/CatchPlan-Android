package com.kauproject.kausanhak.presentation.ui

import android.content.Context
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.pageanimation.noAnimatedComposable
import com.kauproject.kausanhak.presentation.ui.calendar.CalendarScreen
import com.kauproject.kausanhak.presentation.ui.chatbot.ChatBotScreen
import com.kauproject.kausanhak.presentation.ui.event.EventDestination
import com.kauproject.kausanhak.presentation.ui.event.EventDetailScreen
import com.kauproject.kausanhak.presentation.ui.event.EventScreen
import com.kauproject.kausanhak.presentation.ui.favorite.FavoriteScreen
import com.kauproject.kausanhak.presentation.ui.mypage.MyPageScreen
import com.kauproject.kausanhak.presentation.ui.theme.CALENDAR
import com.kauproject.kausanhak.presentation.ui.theme.CHATBOT
import com.kauproject.kausanhak.presentation.ui.theme.EVENT
import com.kauproject.kausanhak.presentation.ui.theme.FAVORITE
import com.kauproject.kausanhak.presentation.ui.theme.MYPAGE

// 메인뷰
@Composable
fun MainScreen(
    onLoginScreen: () -> Unit
){
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Calendar.screenRoute
        ){
        noAnimatedComposable(route = BottomNavItem.Calendar.screenRoute){
            CalendarScreen(navController = navController)
        }
        noAnimatedComposable(route = BottomNavItem.Event.screenRoute){
            EventScreen(
                onEventClick = {id: Int ->
                    navController.navigate("${EventDestination.EVENT_DETAIL_ROUTE}/$id")},
                navController = navController)
        }
        noAnimatedComposable(route = BottomNavItem.Favorite.screenRoute){
            FavoriteScreen(navController = navController)
        }
        noAnimatedComposable(route = BottomNavItem.Chatbot.screenRoute){
            ChatBotScreen(navController = navController)
        }
        noAnimatedComposable(route = BottomNavItem.Mypage.screenRoute){
            MyPageScreen(
                navController = navController,
                onLoginScreen = onLoginScreen,
                context = context
            )
        }

        composable(
            route = "${EventDestination.EVENT_DETAIL_ROUTE}/{${EventDestination.EVENT_DETAIL_ID}}",
            arguments = listOf(navArgument(EventDestination.EVENT_DETAIL_ID){
                type = NavType.IntType }
            )
        ){backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val eventId = arguments.getInt(EventDestination.EVENT_DETAIL_ID)

            EventDetailScreen(
                eventId = eventId,
                navController = navController)
        }

    }



}
// 하단바 구성
@Composable
fun CatchPlanBottomBar(
    navController: NavHostController,
    currentRoute: String
){
    val screens = listOf(
        BottomNavItem.Calendar,
        BottomNavItem.Event,
        BottomNavItem.Favorite,
        BottomNavItem.Chatbot,
        BottomNavItem.Mypage
    )

    NavigationBar(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .shadow(elevation = 20.dp, shape = RoundedCornerShape(15.dp))
        ,
        containerColor = Color.White
    ){
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
                    indicatorColor = colorResource(id = R.color.purple_select)
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
        CALENDAR
    )
    object Event: BottomNavItem(
        R.string.event_bottomItem,
        R.drawable.ic_event.toInt(),
        EVENT
    )
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