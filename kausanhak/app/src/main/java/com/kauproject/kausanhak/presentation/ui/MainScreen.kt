package com.kauproject.kausanhak.presentation.ui

import android.media.Image
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.anim.pageanimation.noAnimatedComposable
import com.kauproject.kausanhak.presentation.ui.calendar.CalendarScreen
import com.kauproject.kausanhak.presentation.ui.chatbot.ChatBotScreen
import com.kauproject.kausanhak.presentation.ui.event.EventDestination
import com.kauproject.kausanhak.presentation.ui.event.EventDetailScreen
import com.kauproject.kausanhak.presentation.ui.event.EventListScreen
import com.kauproject.kausanhak.presentation.ui.event.EventScreen
import com.kauproject.kausanhak.presentation.ui.favorite.FavoriteScreen
import com.kauproject.kausanhak.presentation.ui.mypage.MyPageScreen
import com.kauproject.kausanhak.presentation.ui.theme.CALENDAR
import com.kauproject.kausanhak.presentation.ui.theme.CHATBOT
import com.kauproject.kausanhak.presentation.ui.theme.EVENT
import com.kauproject.kausanhak.presentation.ui.theme.FAVORITE
import com.kauproject.kausanhak.presentation.ui.theme.MYPAGE
import kotlin.io.encoding.Base64
import kotlin.random.Random

private const val animDurationMillis = 400
// 메인뷰
@Composable
fun MainScreen(
    onLoginScreen: () -> Unit
){
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Event.screenRoute
        ){
        noAnimatedComposable(route = BottomNavItem.Calendar.screenRoute){
            CalendarScreen(
                navController = navController,
                onEventClick = { id ->
                    navController.navigate("${EventDestination.EVENT_DETAIL_ROUTE}/$id")
                }
            )
        }
        noAnimatedComposable(route = BottomNavItem.Event.screenRoute){
            EventScreen(
                onEventClick = {id: Int ->
                    navController.navigate("${EventDestination.EVENT_DETAIL_ROUTE}/$id")},
                onArrowClick = {id: Int ->
                    navController.navigate("${EventDestination.EVENT_ARROW_ROUTE}/$id")
                },
                navController = navController
            )
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

        composable(
            route = "${EventDestination.EVENT_ARROW_ROUTE}/{${EventDestination.EVENT_ARROW_ID}}",
            arguments = listOf(navArgument(EventDestination.EVENT_ARROW_ID){
                type = NavType.IntType }
            ),
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(animDurationMillis))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(animDurationMillis))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(animDurationMillis))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(animDurationMillis))
            },
        ){ backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val eventCollectionId = arguments.getInt(EventDestination.EVENT_ARROW_ID)

            EventListScreen(
                eventCollectionId = eventCollectionId,
                onEventClick = {id: Int ->
                    navController.navigate("${EventDestination.EVENT_DETAIL_ROUTE}/$id")},
                navController = navController,
            )
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
        BottomNavItem.Favorite,
        BottomNavItem.Event,
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
                    Icon(
                        modifier = Modifier
                            .size(25.dp)
                        ,
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
                    indicatorColor = Color.Transparent,
                    selectedIconColor = colorResource(id = R.color.purple_main),
                    unselectedIconColor = Color.Black
                )
            )
        }
        val imageVector = Icons.AutoMirrored.Outlined.Send

    }

}

// 하단바 아이템 설정
sealed class BottomNavItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val screenRoute: String
){
    object Calendar: BottomNavItem(
        R.string.calendar_bottomItem,
        Icons.Outlined.DateRange,
        CALENDAR
    )
    object Event: BottomNavItem(
        R.string.event_bottomItem,
        Icons.Outlined.Menu,
        EVENT
    )
    object Favorite: BottomNavItem(
        R.string.favorite_bottomItem,
        Icons.Outlined.FavoriteBorder,
        FAVORITE
    )
    object Chatbot: BottomNavItem(
        R.string.chatBot_bottomItem,
        Icons.AutoMirrored.Outlined.Send,
        CHATBOT
    )
    object Mypage: BottomNavItem(
        R.string.myPage_bottomItem,
        Icons.Outlined.AccountCircle,
        MYPAGE
    )
}