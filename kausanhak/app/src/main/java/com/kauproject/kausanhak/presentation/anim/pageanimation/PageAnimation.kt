package com.kauproject.kausanhak.presentation.anim.pageanimation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val animDurationMillis = 400
private typealias SlideDirection = AnimatedContentTransitionScope.SlideDirection

fun NavGraphBuilder.horizontallyAnimatedComposable(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
){
    composable(
        route = route,
        content = content,
        enterTransition = {
            slideIntoContainer(SlideDirection.Left, animationSpec = tween(animDurationMillis))
        },
        exitTransition = {
            slideOutOfContainer(SlideDirection.Left, animationSpec = tween(animDurationMillis))
        },
        popEnterTransition = {
            slideIntoContainer(SlideDirection.Right, animationSpec = tween(animDurationMillis))
        },
        popExitTransition = {
            slideOutOfContainer(SlideDirection.Right, animationSpec = tween(animDurationMillis))
        },
    )
}

fun NavGraphBuilder.verticallyAnimatedComposable(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        content = content,
        enterTransition = {
            slideIntoContainer(SlideDirection.Up, animationSpec = tween(animDurationMillis))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(animDurationMillis))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(animDurationMillis))
        },
        popExitTransition = {
            slideOutOfContainer(SlideDirection.Down, animationSpec = tween(animDurationMillis))
        },
    )
}

fun NavGraphBuilder.verticallyArgumentAnimatedComposable(
    route: String,
    arguments: List<NamedNavArgument>,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        arguments = arguments,
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
    ) { backStackEntry ->
        content(backStackEntry)
    }
}


fun NavGraphBuilder.noAnimatedComposable(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
){
    composable(
        route = route,
        content = content,
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 0))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 0))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 0))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 0))
        },
    )

}