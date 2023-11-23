package com.kauproject.kausanhak.presentation.ui.upload

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.kauproject.kausanhak.BuildConfig
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.anim.lottieanimation.LottieUpLoadAnimation
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun UpLoadScreen(
    onDoneButton: () -> Unit,
    onCancelButton: () -> Unit
){
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.White
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(0.20f)
                ,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = stringResource(id = R.string.upload_subTitle),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Text(
                    text = stringResource(id = R.string.upload_title),
                    fontSize = 36.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(id = R.color.purple_main)
                )
                Text(
                    text = stringResource(id = R.string.upload_money),
                    color = colorResource(id = R.color.gray_3),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }

            LottieUpLoadAnimation(
                modifier = Modifier
                    .weight(0.5f)
            )

            Column(
                modifier = Modifier
                    .weight(0.30f)
                ,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                    ,
                    onClick = onDoneButton,
                    colors = ButtonDefaults.buttonColors(
                        colorResource(id = R.color.purple_main)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 2.dp),
                        text = stringResource(id = R.string.upload_ok),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                    ,
                    onClick = onCancelButton,
                    colors = ButtonDefaults.buttonColors(
                        Color.LightGray
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 2.dp),
                        text = stringResource(id = R.string.upload_no),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }

    }
}

