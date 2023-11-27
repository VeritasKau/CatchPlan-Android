package com.kauproject.kausanhak.presentation.ui.upload.uploadstep

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.net.toUri
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.kauproject.kausanhak.BuildConfig
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import com.kauproject.kausanhak.presentation.ui.upload.UpLoadFormViewModel
import com.kauproject.kausanhak.presentation.ui.upload.uploadstep.dialog.UpLoadPosterDialog
import com.kauproject.kausanhak.presentation.util.UriUtil
import com.kauproject.kausanhak.presentation.util.clickable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Objects

@Composable
fun UpLoadPoster(
    modifier: Modifier,
    viewModel: UpLoadFormViewModel
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.upload_image_title),
            fontSize = 20.sp,
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        PreviewPromotionCard(viewModel = viewModel)

    }
}

@Composable
private fun PreviewPromotionCard(
    viewModel: UpLoadFormViewModel
){
    var capturedImageUri by remember { mutableStateOf<Uri?>(viewModel.mainImageUri) }
    var showDialog by remember { mutableStateOf(false) }
    var showGallery by remember { mutableStateOf(false) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { it: Uri? ->
            it?.let { capturedImageUri = it }
        })

    // 다이얼로그 표시
    if(showDialog){
        UpLoadPosterDialog(
            showDialog = { showDialog = it },
            showGallery = { showGallery = it }
        )
    }

    // 갤러리 접근
    if(showGallery){
        galleryLauncher.launch("image/*")
    }

    Card(
        modifier = Modifier
            .width(300.dp)
            .height(400.dp)
            .shadow(shape = RoundedCornerShape(10.dp), elevation = 5.dp)
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.9f)
        ) {
            if(capturedImageUri?.path?.isNotEmpty() == true || capturedImageUri != null){
                // 갤러리 접근 해제
                showGallery = false

                viewModel.onUpLoadPoster(uri = capturedImageUri)

                Image(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    painter = rememberAsyncImagePainter(capturedImageUri)
                    ,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds

                )
            }else{
                showGallery = false
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { showDialog = true }
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                        ,
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null
                    )
                    Text(
                        text = "사진등록",
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }

            }

        }
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .clickable { showDialog = true }
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                ,
                text = "다시 등록하기",
                fontWeight = FontWeight.Bold,
                color = Color.LightGray
            )
        }

    }

}