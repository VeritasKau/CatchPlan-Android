package com.kauproject.kausanhak.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.anim.pageanimation.horizontallyAnimatedComposable
import com.kauproject.kausanhak.presentation.anim.pageanimation.noAnimatedComposable
import com.kauproject.kausanhak.presentation.anim.pageanimation.verticallyAnimatedComposable
import com.kauproject.kausanhak.presentation.ui.calendar.CalendarScreen
import com.kauproject.kausanhak.presentation.ui.chatbot.ChatBotScreen
import com.kauproject.kausanhak.presentation.ui.event.EventScreen
import com.kauproject.kausanhak.presentation.ui.event.detail.EventDetailScreen
import com.kauproject.kausanhak.presentation.ui.event.list.EventListScreen
import com.kauproject.kausanhak.presentation.ui.mypage.MyPageScreen
import com.kauproject.kausanhak.presentation.ui.mypage.profile.ProfileScreen
import com.kauproject.kausanhak.presentation.ui.promotion.PromotionScreen
import com.kauproject.kausanhak.presentation.ui.recommend.RecommendScreen
import com.kauproject.kausanhak.presentation.ui.scrap.ScrapScreen
import com.kauproject.kausanhak.presentation.ui.theme.CALENDAR
import com.kauproject.kausanhak.presentation.ui.theme.CHATBOT
import com.kauproject.kausanhak.presentation.ui.theme.EVENT
import com.kauproject.kausanhak.presentation.ui.theme.MYPAGE
import com.kauproject.kausanhak.presentation.ui.theme.PROMOTION
import com.kauproject.kausanhak.presentation.ui.theme.RECOMMEND
import com.kauproject.kausanhak.presentation.ui.upload.UpLoadFormRoute
import com.kauproject.kausanhak.presentation.ui.upload.UpLoadScreen
import com.kauproject.kausanhak.presentation.util.icon.IconPack
import com.kauproject.kausanhak.presentation.util.icon.iconpack.Promotion
import com.kauproject.kausanhak.presentation.util.icon.iconpack.Recommend

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
                    navController.navigate("${Destination.EVENT_DETAIL_ROUTE}/$id")
                }
            )
        }
        noAnimatedComposable(route = BottomNavItem.Event.screenRoute){
            EventScreen(
                onEventClick = {id: Int ->
                    navController.navigate("${Destination.EVENT_DETAIL_ROUTE}/$id")},
                onArrowClick = {id: Int ->
                    navController.navigate("${Destination.EVENT_ARROW_ROUTE}/$id")
                },
                navController = navController
            )
        }

        noAnimatedComposable(route = BottomNavItem.Mypage.screenRoute){
            MyPageScreen(
                navController = navController,
                onLoginScreen = onLoginScreen,
                onScrapScreen = { navController.navigate(Destination.EVENT_SCRAP_ROUTE) },
                onUpLoadScreen = { navController.navigate(Destination.UPLOAD_ROUTE) },
            )
        }

        verticallyAnimatedComposable(route = Destination.UPLOAD_ROUTE){
            UpLoadScreen(
                onDoneButton = { navController.navigate(Destination.UPLOAD_FORM_ROUTE) },
                onCancelButton = { navController.navigateUp() }
            )
        }
        
        horizontallyAnimatedComposable(route = Destination.UPLOAD_FORM_ROUTE){
            UpLoadFormRoute(
                navController = navController,
                onClosePressed = {
                    navController.navigate(
                        route = BottomNavItem.Mypage.screenRoute,
                        navOptions = NavOptions.Builder().setPopUpTo(Destination.UPLOAD_ROUTE, inclusive = true).build()
                    )
                },
                onComplete = {
                    navController.navigate(
                        route = BottomNavItem.Mypage.screenRoute,
                        navOptions = NavOptions.Builder().setPopUpTo(Destination.UPLOAD_ROUTE, inclusive = true).build()
                    )
                }
            )
        }

        noAnimatedComposable(route = BottomNavItem.Promotion.screenRoute){
            PromotionScreen(navController = navController)
        }

        noAnimatedComposable(route = BottomNavItem.Recommend.screenRoute){
            RecommendScreen(navController = navController)
        }

        composable(
            route = "${Destination.EVENT_DETAIL_ROUTE}/{${Destination.EVENT_DETAIL_ID}}",
            arguments = listOf(navArgument(Destination.EVENT_DETAIL_ID){
                type = NavType.IntType }
            )
        ){backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val eventId = arguments.getInt(Destination.EVENT_DETAIL_ID)

            EventDetailScreen(
                eventId = eventId,
                navController = navController)
        }

        composable(
            route = "${Destination.EVENT_ARROW_ROUTE}/{${Destination.EVENT_ARROW_ID}}",
            arguments = listOf(navArgument(Destination.EVENT_ARROW_ID){
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
            val eventCollectionId = arguments.getInt(Destination.EVENT_ARROW_ID)

            EventListScreen(
                eventCollectionId = eventCollectionId,
                onEventClick = {id: Int ->
                    navController.navigate("${Destination.EVENT_DETAIL_ROUTE}/$id")},
                navController = navController,
            )
        }
        
        verticallyAnimatedComposable(route = Destination.CHAT_BOT_ROUTE){
            ChatBotScreen(navController = navController)
        }

        horizontallyAnimatedComposable(route = Destination.EVENT_SCRAP_ROUTE){
            ScrapScreen(
                onEventClick = {id: Int ->
                    navController.navigate("${Destination.EVENT_DETAIL_ROUTE}/$id")},
                navController = navController
            )
        }

        horizontallyAnimatedComposable(route = Destination.MYPAGE_PROFILE){
            ProfileScreen(
                navController = navController
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
        BottomNavItem.Promotion,
        BottomNavItem.Event,
        BottomNavItem.Recommend,
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
                    unselectedIconColor = Color.LightGray,
                    selectedTextColor = colorResource(id = R.color.purple_main),
                    unselectedTextColor = Color.LightGray
                ),
            )
        }

    }

}

// 하단바 아이템 설정
sealed class BottomNavItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val screenRoute: String,
    @StringRes val name: Int
){
    object Calendar: BottomNavItem(
        R.string.calendar_bottomItem,
        Icons.Outlined.DateRange,
        CALENDAR,
        R.string.bottom_calendar
    )
    object Event: BottomNavItem(
        R.string.event_bottomItem,
        Icons.Outlined.Menu,
        EVENT,
        R.string.bottom_main
    )
    object Mypage: BottomNavItem(
        R.string.myPage_bottomItem,
        Icons.Outlined.AccountCircle,
        MYPAGE,
        R.string.bottom_mypage
    )
    object Promotion: BottomNavItem(
        R.string.promotion_bottomItem,
        IconPack.Promotion,
        PROMOTION,
        R.string.bottom_promotion
    )
    object Recommend: BottomNavItem(
        R.string.bottom_recommend,
        IconPack.Recommend,
        RECOMMEND,
        R.string.bottom_recommend
    )
}