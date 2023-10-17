package com.kauproject.kausanhak.presentation.ui.setting.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.setting.SettingViewModel
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme

@Composable
fun MbtiDialog(
    showDialog: (Boolean) -> Unit,
    setMbtiData: (String) -> Unit
){
    val viewModel: SettingViewModel = viewModel()
    Dialog(onDismissRequest = { showDialog(false) }) {
        Surface(
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ) {
            MbtiDialogContent(
                viewModel = viewModel,
                showDialog = showDialog,
                setMbtiData = setMbtiData
                )
        }
    }
}
@Composable
fun MbtiDialogContent(
    viewModel: SettingViewModel,
    showDialog: (Boolean) -> Unit,
    setMbtiData: (String) -> Unit
){
    val choiceMbti by viewModel.choiceMbti.collectAsState()

    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
        ,
    ) {
        Text(
            modifier = Modifier
                .padding(start = 15.dp)
            ,
            text = stringResource(id = R.string.setting_mbti_title)
        )

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        CreateMbtiSetButton(viewModel, firstMbti, 0)
        CreateMbtiSetButton(viewModel, secondMbti, 1)
        CreateMbtiSetButton(viewModel, thirdMbti, 2)
        CreateMbtiSetButton(viewModel, lastMbti, 3)

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp)
            ,
            Arrangement.End
        ){
            Text(
                modifier = Modifier
                    .clickable(
                        enabled = (choiceMbti.first != "" && choiceMbti.second != "" &&
                                choiceMbti.third != "" && choiceMbti.last != "")
                    ){
                        val mbti = choiceMbti.first + choiceMbti.second + choiceMbti.third + choiceMbti.last
                        setMbtiData(mbti)
                        resetMbti(viewModel)
                        showDialog(false)
                    }
                ,
                text = stringResource(id = R.string.setting_dialog_ok),
                textAlign = TextAlign.End
            )
        }

    }

}
@Composable
fun CreateMbtiSetButton(
    viewModel: SettingViewModel,
    data: List<String>,
    mbtiIndex: Int,
){
    var selectedButtonIndex by remember { mutableStateOf(-1) } // 선택된 버튼의 인덱스를 저장하는 변수

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 10.dp)
        ,
        verticalArrangement = Arrangement.Center,
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(data) { index, item ->
            val isSelected = selectedButtonIndex == index
            val bgColor = if (isSelected) colorResource(id = R.color.purple_main) else Color.White
            val txtColor = if (isSelected) Color.White else colorResource(id = R.color.purple_main)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                    ,
                    border = BorderStroke(0.5.dp, color = colorResource(id = R.color.purple_main)),
                    colors = ButtonDefaults.buttonColors(containerColor = bgColor),
                    onClick = {
                        selectedButtonIndex = index // 클릭된 버튼의 인덱스를 저장
                        when(mbtiIndex){
                            0 -> viewModel.setFirstMbti(item)
                            1 -> viewModel.setSecondMbti(item)
                            2 -> viewModel.setThirdMbti(item)
                            3 -> viewModel.setLastMbti(item)
                        }
                    }
                ) {
                    Text(
                        color = txtColor,
                        text = item
                    )
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MbtiDialogPreview(){
    KausanhakTheme {
        MbtiDialog(showDialog = {}, setMbtiData = {})
    }
}

// Reset Mbti: When close dialog
private fun resetMbti(
    viewModel: SettingViewModel
){
    viewModel.setFirstMbti("")
    viewModel.setSecondMbti("")
    viewModel.setThirdMbti("")
    viewModel.setLastMbti("")
}


private val firstMbti = listOf(
    "E", "I"
)
private val secondMbti = listOf(
    "N", "S"
)
private val thirdMbti = listOf(
    "T", "F"
)
private val lastMbti = listOf(
    "J", "P"
)