package com.kauproject.kausanhak.presentation.ui.calendar.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.calendar.CalendarScreenViewModel

// to-do List 작성
@Composable
fun ShowMemoDialog(
    showDialog: (Boolean) -> Unit,
    viewModel: CalendarScreenViewModel,
    date: String
){
    var textFieldState by remember{ mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Dialog(onDismissRequest = { showDialog(false) }) {
        Surface(
            modifier = Modifier
                .width(400.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ){
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                    ,
                ){
                    Image(
                        modifier = Modifier
                            .size(30.dp)
                        ,
                        painter = painterResource(id = R.drawable.ic_app_icon),
                        contentDescription = null
                    )
                    Text(
                        text = "To-do List",
                        fontWeight = FontWeight.Bold
                    )
                }
                OutlinedTextField(
                    value = textFieldState,
                    onValueChange = {
                        textFieldState = it
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.calendar_dialog_todo_hint),
                            color = Color.LightGray
                        )
                    },
                    enabled = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black
                    )
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
                            text = stringResource(id = R.string.calendar_dialog_cancel),
                            color = Color.Black
                        )
                    }
                    TextButton(
                        onClick = {
                            viewModel.addMemo(
                                date = date,
                                content = textFieldState
                            )
                            showDialog(false)
                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.calendar_dialog_ok),
                            color = colorResource(id = R.color.purple_main),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

        }

    }

}