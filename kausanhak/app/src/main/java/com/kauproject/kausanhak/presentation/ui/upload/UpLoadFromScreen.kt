package com.kauproject.kausanhak.presentation.ui.upload

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kauproject.kausanhak.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpLoadFromScreen(
    upLoadFormScreenData: UpLoadFormScreenData,
    isNextEnabled: Boolean,
    onClosePressed: () -> Unit,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onCompletePressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
    ){
    Surface(
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
            ,
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
                    shouldShowPreviousButton = upLoadFormScreenData.shouldShowPreviousButton,
                    shouldShowCompleteButton = upLoadFormScreenData.shouldShowCompleteButton,
                    isNextButtonEnabled = isNextEnabled,
                    onPreviousPressed = onPreviousPressed,
                    onNextPressed = onNextPressed,
                    onCompletePressed = onCompletePressed
                )
            }
        )
    }

}

@Composable
private fun UpLoadFormBottomBar(
    shouldShowPreviousButton: Boolean,
    shouldShowCompleteButton: Boolean,
    isNextButtonEnabled: Boolean,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onCompletePressed: () -> Unit
){
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
                    onClick = onCompletePressed,
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