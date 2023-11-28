package com.kauproject.kausanhak.presentation.ui.upload.uploadstep

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.upload.UpLoadFormViewModel
import com.kauproject.kausanhak.presentation.ui.upload.uploadstep.dialog.UpLoadPosterDialog
import com.kauproject.kausanhak.presentation.util.clickable

@Composable
fun Content(
    modifier: Modifier,
    viewModel: UpLoadFormViewModel
){
    Column(
        modifier = modifier
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxSize()
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var titleTextFieldState by remember{ mutableStateOf(viewModel.title) }
        val maxChar = 20
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

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = titleTextFieldState,
            onValueChange = {
                if (it.length <= maxChar) {
                    titleTextFieldState = it
                }
            },
            shape = RoundedCornerShape(5.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
                cursorColor = Color.Black
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.upload_set_title),
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold
                )
            },
        )
        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        var contentTextFieldState by remember { mutableStateOf(viewModel.content) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
                ,
                value = contentTextFieldState,
                onValueChange = { contentTextFieldState = it },
                shape = RoundedCornerShape(5.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = Color.Black
                ),
                placeholder = {
                    Column {
                        Text(
                            text = stringResource(id = R.string.upload_content_title),
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        Text(
                            text = stringResource(id = R.string.upload_content_subTitle),
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                },)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .weight(0.2f)
                ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(capturedImageUri != null){
                    showGallery = false
                    viewModel.onContentImage(capturedImageUri)

                    Box(modifier = Modifier.fillMaxWidth()){
                        Image(
                            modifier = Modifier
                                .width(100.dp)
                                .height(200.dp)
                                .border(1.dp, Color.Transparent, RoundedCornerShape(5.dp))
                            ,
                            painter = rememberAsyncImagePainter(capturedImageUri)
                            ,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(200.dp)
                            ,
                        ) {
                            IconButton(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                ,
                                onClick = { capturedImageUri = null }
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(30.dp)
                                    ,
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }else{
                    showGallery = false
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp)
                                .padding(vertical = 10.dp)
                                .clickable { showDialog = true }
                            ,
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = null,
                            tint = Color.LightGray
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showDialog = true }
                            ,
                            text = "사진 첨부",
                            fontWeight = FontWeight.Bold,
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
        viewModel.onWriteContent(titleTextFieldState, contentTextFieldState)

    }

}