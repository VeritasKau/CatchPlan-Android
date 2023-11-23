package com.kauproject.kausanhak.presentation.ui.upload.uploadstep.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme

@Composable
fun UpLoadPosterDialog(
    showDialog: (Boolean) -> Unit,
    showCamera: (Boolean) -> Unit,
    showGallery: (Boolean) -> Unit
){
    Dialog(onDismissRequest = { showDialog(false) }) {
        Surface(
            modifier = Modifier
                .width(400.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ){
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                TextButton(
                    onClick = {
                        showCamera(true)
                        showDialog(false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.upload_image_dialog_camera),
                        fontSize = 18.sp
                    )
                }
                TextButton(
                    onClick = {
                        showGallery(true)
                        showDialog(false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.upload_image_dialog_gallery),
                        fontSize = 18.sp
                    )
                }
                TextButton(
                    onClick = { showDialog(false) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.upload_image_dialog_cancel),
                        fontSize = 18.sp,
                        color = Color.Red
                    )
                }
            }

        }
    }

}
