package com.kauproject.kausanhak.presentation.ui.upload

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kauproject.kausanhak.presentation.PurchaseHelper
import com.kauproject.kausanhak.presentation.ui.upload.uploadstep.Content
import com.kauproject.kausanhak.presentation.ui.upload.uploadstep.Date
import com.kauproject.kausanhak.presentation.ui.upload.uploadstep.Preview
import com.kauproject.kausanhak.presentation.ui.upload.uploadstep.UpLoadPoster

private const val CONTENT_ANIMATION_DURATION = 300

@Composable
fun UpLoadFormRoute(
    navController: NavHostController,
    onClosePressed: () -> Unit,
    onComplete: () -> Unit,
    purchaseHelper: PurchaseHelper
){
    val viewModel: UpLoadFormViewModel = hiltViewModel()
    val upLoadFormScreenData = viewModel.upLoadFormScreenData

    BackHandler {
        if(!viewModel.onBackPressed()){
            navController.navigateUp()
        }
    }

    UpLoadFormScreen(
        viewModel = viewModel,
        upLoadFormScreenData = upLoadFormScreenData,
        isNextEnabled = viewModel.isNextEnabled,
        onClosePressed = onClosePressed,
        onPreviousPressed = { viewModel.onPreviousPressed() },
        onNextPressed = { viewModel.onNextPressed() },
        onCompletePressed = { viewModel.onCompletePressed(onComplete) },
        purchaseHelper = purchaseHelper
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)

        AnimatedContent(
            targetState = upLoadFormScreenData,
            transitionSpec = {
                val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)

                val direction = getTransitionDirection(
                    initialIndex = initialState.stepIndex,
                    targetIndex = targetState.stepIndex
                )

                slideIntoContainer(
                    towards = direction,
                    animationSpec = animationSpec
                ) togetherWith slideOutOfContainer(
                    towards = direction,
                    animationSpec = animationSpec
                )
            },
            label = ""
        ) { targetState ->
            when(targetState.upLoadStep){
                UpLoadFormViewModel.UpLoadStep.CONTENT -> {
                    Content(
                        modifier = modifier,
                        viewModel = viewModel
                    )
                }
                UpLoadFormViewModel.UpLoadStep.IMAGE -> {
                    UpLoadPoster(
                        modifier = modifier,
                        viewModel = viewModel
                    )
                }
                UpLoadFormViewModel.UpLoadStep.DATE_PICK -> {
                    Date(
                        modifier = modifier,
                        viewModel = viewModel
                    )
                }
                UpLoadFormViewModel.UpLoadStep.PREVIEW -> {
                    Preview(
                        modifier = modifier,
                        viewModel = viewModel
                    )
                }
            }

        }
    }

}

private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentTransitionScope.SlideDirection{
    return if(targetIndex > initialIndex){
        AnimatedContentTransitionScope.SlideDirection.Left
    }else{
        AnimatedContentTransitionScope.SlideDirection.Right
    }
}