package com.kauproject.kausanhak.presentation.ui.upload.uploadstep

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.upload.UpLoadFormViewModel

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

        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize()
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
        viewModel.onWriteContent(titleTextFieldState, contentTextFieldState)

    }

}