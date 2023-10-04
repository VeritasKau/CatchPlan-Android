package com.kauproject.kausanhak.presentation.ui.setting

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.ui.theme.KausanhakTheme

@Composable
fun MbtiSettingScreen(
    onNextButtonClick: () -> Unit
) {
    val viewModel: MbtiSettingViewModel = viewModel()
    val userMbti by viewModel.userMbti.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
        ,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            Text(
                modifier = Modifier
                ,
                text = stringResource(id = R.string.setting_title),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Text(
                text = stringResource(id = R.string.setting_title_mbti),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp
            )
            Text(
                text = stringResource(id = R.string.setting_sub_title),
                color = Color.LightGray
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 30.dp))

        SetMbtiButton(viewModel = viewModel)

        Spacer(modifier = Modifier.padding(vertical = 30.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp)
            ,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_main)),
            onClick = onNextButtonClick,
            shape = RoundedCornerShape(15.dp),
            enabled = userMbti.mbti != ""
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.setting_complete),
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
fun SetMbtiButton(
    viewModel: MbtiSettingViewModel
){
    var selectedButtonIndex by remember { mutableStateOf(-1) } // 선택된 버튼의 인덱스를 저장하는 변수

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 10.dp)
        ,
        verticalArrangement = Arrangement.Center,
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(mbtiData) { index, item ->
            val isSelected = selectedButtonIndex == index
            val bgColor = if (isSelected) colorResource(id = R.color.purple_main) else Color.White
            val txtColor = if (isSelected) Color.White else colorResource(id = R.color.purple_main)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                    ,
                    border = BorderStroke(0.5.dp, color = colorResource(id = R.color.purple_main)),
                    colors = ButtonDefaults.buttonColors(containerColor = bgColor),
                    onClick = {
                        viewModel.setUserMbti(item)
                        selectedButtonIndex = index // 클릭된 버튼의 인덱스를 저장
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
fun PreviewSettingScreen(){
    KausanhakTheme {
        MbtiSettingScreen(
            onNextButtonClick = {}
        )
    }
}