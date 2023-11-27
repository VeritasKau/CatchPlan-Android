package com.kauproject.kausanhak.presentation.ui.upload.uploadstep

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import com.kauproject.kausanhak.presentation.ui.upload.UpLoadFormViewModel
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun Date(
    modifier: Modifier,
    viewModel: UpLoadFormViewModel
){
  
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        PlaceTextField(viewModel = viewModel)
        Spacer(modifier = Modifier.padding(vertical = 30.dp))
        URLTextField(viewModel = viewModel)
        Spacer(modifier = Modifier.padding(vertical = 30.dp))
        DateButton(viewModel = viewModel)
    }


}

@Composable
private fun PlaceTextField(
    viewModel: UpLoadFormViewModel
){
    var placeTextFieldState by remember{ mutableStateOf(viewModel.place) }

    Text(
        text = stringResource(id = R.string.upload_place_title),
        fontSize = 20.sp,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.padding(vertical = 5.dp))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = placeTextFieldState,
        onValueChange = { placeTextFieldState = it },
        shape = RoundedCornerShape(5.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Black
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.upload_place_hint),
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )
        },
    )
    viewModel.onWritePlace(placeTextFieldState)

}

@Composable
private fun URLTextField(
    viewModel: UpLoadFormViewModel
){
    var urlTextFieldState by remember{ mutableStateOf(viewModel.url) }

    Text(
        text = stringResource(id = R.string.upload_url_title),
        fontSize = 20.sp,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = stringResource(id = R.string.upload_url_subTitle),
        fontSize = 16.sp,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        color = Color.LightGray
    )
    Spacer(modifier = Modifier.padding(vertical = 5.dp))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = urlTextFieldState ?: "",
        onValueChange = { urlTextFieldState = it },
        shape = RoundedCornerShape(5.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Black
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.upload_url_hint),
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )
        },
    )
    viewModel.onWriteURL(urlTextFieldState)
}

@Composable
private fun DateButton(
    viewModel: UpLoadFormViewModel
){
    var showStartDialog by remember{ mutableStateOf(false) }
    var showEndDialog by remember{ mutableStateOf(false) }
    var start by remember{ mutableStateOf(viewModel.startDate) }
    var end by remember { mutableStateOf(viewModel.endDate) }
    val startColor = if(start != "시작날짜 선택") Color.Black else Color.LightGray
    val endColor = if(end != "종료날짜 선택") Color.Black else Color.LightGray

    if(showStartDialog){
        ShowDatePicker(
            onDismiss = { showStartDialog = false },
            onDateSelected = { start = it })
    }

    if(showEndDialog){
        ShowDatePicker(
            onDismiss = { showEndDialog = false },
            onDateSelected = { end = it })
    }

    Text(
        text = stringResource(id = R.string.upload_date_title),
        fontSize = 20.sp,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.padding(vertical = 5.dp))
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
        ,
        onClick = { showStartDialog = true },
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Text(
            text = start,
            color = startColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

    }
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
        ,
        onClick = { showEndDialog = true },
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Text(
            modifier = Modifier
            ,
            text = end,
            color = endColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
    viewModel.onSelectedDate(start, end)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDatePicker(
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
){
    val currentMills = System.currentTimeMillis()
    val LAVENDAR = colorResource(id = R.color.lavender_3)

    val datePickerState = rememberDatePickerState(
        initialDisplayedMonthMillis = currentMills,
        initialSelectedDateMillis = currentMills,
        selectableDates = object : SelectableDates{
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= currentMills
            }
        }
    )

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        // 색상 커스텀
        colors = DatePickerDefaults.colors(
            containerColor = Color.White,
        )
        ,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                onClick = {
                    onDateSelected(selectedDate)
                    onDismiss()
                }

            ) {
                Text(text = "확인")
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                onClick = {
                    onDismiss()
                }){
                Text(text = "취소")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = LAVENDAR,
                todayDateBorderColor = LAVENDAR,
                todayContentColor = Color.Black
            )
        )
    }
}

private fun convertMillisToDate(millis: Long): String {
    return Instant.ofEpochMilli(millis)
        .atOffset(ZoneOffset.ofHours(9))
        .format(DateTimeFormatter.ofPattern("uuuu-MM-dd"))
}


@Preview(showBackground = true)
@Composable
private fun PreviewDate(){
    val viewModel: UpLoadFormViewModel = hiltViewModel()
    KausanhakTheme {
        Date(modifier = Modifier, viewModel = viewModel)
    }
}

