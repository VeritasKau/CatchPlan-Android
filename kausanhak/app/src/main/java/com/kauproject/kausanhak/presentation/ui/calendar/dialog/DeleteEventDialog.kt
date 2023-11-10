package com.kauproject.kausanhak.presentation.ui.calendar.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.calendar.CalendarScreenViewModel

@Composable
fun ShowDeleteDialog(
    showDialog: (Boolean) -> Unit,
    viewModel: CalendarScreenViewModel,
    eventId: Int
) {
    Dialog(onDismissRequest = { showDialog(false) }) {
        Surface(
            modifier = Modifier
                .width(400.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                ,
            ) {
                Spacer(modifier = Modifier.padding(vertical = 25.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    text = stringResource(id = R.string.calendar_dialog_subtitle),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(vertical = 15.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextButton(
                        onClick = { showDialog(false) }
                    ) {
                        Text(
                            text = stringResource(id = R.string.dialog_no),
                            color = Color.Black
                        )
                    }
                    TextButton(
                        onClick = {
                            showDialog(false)
                            viewModel.deleteDate(eventId) },
                    ) {
                        Text(
                            text = stringResource(id = R.string.dialog_yes),
                            color = Color.Red
                        )
                    }
                }
            }


        }
    }
}