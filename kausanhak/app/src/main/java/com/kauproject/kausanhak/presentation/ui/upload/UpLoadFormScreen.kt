package com.kauproject.kausanhak.presentation.ui.upload

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.presentation.anim.lottieanimation.LottieChatAnimation
import com.kauproject.kausanhak.presentation.anim.lottieanimation.LottieLoadingAnimation
import kotlinx.coroutines.launch

@Composable
fun UpLoadFormScreen(
    viewModel: UpLoadFormViewModel,
    upLoadFormScreenData: UpLoadFormScreenData,
    isNextEnabled: Boolean,
    onClosePressed: () -> Unit,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onCompletePressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
    ){
    val snackbarHostState = remember { SnackbarHostState() }
    var isLoading by remember{ mutableStateOf(false) }

    if(isLoading){
        LoadingDialog()
    }

    Surface(
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
            ,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            containerColor = Color.White,
            topBar = {
                UpLoadFormTopBar(
                    stepIndex = upLoadFormScreenData.stepIndex,
                    totalStepCount = upLoadFormScreenData.stepCount,
                    onClosePressed = onClosePressed
                )
            },
            content = content,
            bottomBar = {
                UpLoadFormBottomBar(
                    snackbarHostState = snackbarHostState,
                    shouldShowPreviousButton = upLoadFormScreenData.shouldShowPreviousButton,
                    shouldShowCompleteButton = upLoadFormScreenData.shouldShowCompleteButton,
                    isNextButtonEnabled = isNextEnabled,
                    onPreviousPressed = onPreviousPressed,
                    onNextPressed = onNextPressed,
                    onCompletePressed = onCompletePressed,
                    viewModel = viewModel,
                    isLoading = { isLoading = it }
                )
            }
        )
    }

}

@Composable
private fun UpLoadFormBottomBar(
    snackbarHostState: SnackbarHostState,
    viewModel: UpLoadFormViewModel,
    shouldShowPreviousButton: Boolean,
    shouldShowCompleteButton: Boolean,
    isNextButtonEnabled: Boolean,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onCompletePressed: () -> Unit,
    isLoading: (Boolean) -> Unit
){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
        ,
        shadowElevation = 7.dp,
        contentColor = Color.White,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            if(shouldShowPreviousButton){
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                    ,
                    onClick = onPreviousPressed,
                    border = BorderStroke(1.dp, colorResource(id = R.color.purple_main)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.upload_previous),
                        color = colorResource(id = R.color.purple_main),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            if(shouldShowCompleteButton){
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                    ,
                    onClick = {
                        scope.launch {
                            viewModel.onPostPromotion(context = context).collect{ state->
                                when(state){
                                    is State.Loading -> { isLoading(true) }
                                    is State.Success -> {
                                        isLoading(false)
                                        onCompletePressed()
                                    }
                                    is State.ServerError -> {
                                        snackbarHostState.showSnackbar(
                                            message = "ServerError: ${state.code}",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                    is State.Error -> {
                                        snackbarHostState.showSnackbar(
                                            message = "Error: ${state.exception}",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            }
                        }
                              },
                    enabled = isNextButtonEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.purple_main),
                        disabledContainerColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.upload_complete),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }else{
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                    ,
                    onClick = onNextPressed,
                    enabled = isNextButtonEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.purple_main),
                        disabledContainerColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.upload_next),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

        }

    }

}

@Composable
private fun TopBarTitle(
    stepIndex: Int,
    totalStepCount: Int,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier){
        Text(
            text = (stepIndex + 1).toString(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = stringResource(R.string.upload_count, totalStepCount),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UpLoadFormTopBar(
    stepIndex: Int,
    totalStepCount: Int,
    onClosePressed: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.White),
            title = {
                TopBarTitle(
                    stepIndex = stepIndex,
                    totalStepCount = totalStepCount
                ) },
            actions = {
                IconButton(
                    onClick = onClosePressed,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                       Icons.Filled.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                    )
                }
            }
        )

        val animatedProgress by animateFloatAsState(
            targetValue = (stepIndex + 1) / totalStepCount.toFloat(),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
            label = ""
        )

        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ,
            color = colorResource(id = R.color.purple_main)
        )

    }

}

@Composable
private fun LoadingDialog(){
    Dialog(onDismissRequest = {}) {
        Surface(
            modifier = Modifier.wrapContentSize(),
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ) {
            LottieChatAnimation(modifier = Modifier.wrapContentSize(), isCompleted = false)
        }

    }
}