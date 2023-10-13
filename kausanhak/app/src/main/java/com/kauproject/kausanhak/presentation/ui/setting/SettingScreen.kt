package com.kauproject.kausanhak.presentation.ui.setting

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.pageanimation.horizontallyAnimatedComposable
import com.kauproject.kausanhak.presentation.ui.setting.dialog.FavoriteDialog
import com.kauproject.kausanhak.presentation.ui.setting.dialog.MbtiDialog
import com.kauproject.kausanhak.ui.theme.KausanhakTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onComplete: () -> Unit
){
    val viewModel: SettingViewModel = viewModel()
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(id = R.string.setting_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.padding(10.dp))
            CreateNameOutTextField(viewModel = viewModel)
            HorizontalDivider(modifier = Modifier.padding(bottom = 20.dp))
            CreateGenderButton(viewModel = viewModel)
            HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))
            CreateMbtiButton(viewModel = viewModel)
            HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))
            CreateFavoriteButton(viewModel = viewModel)
            CreateCompleteButton(
                viewModel = viewModel,
                onComplete = onComplete
            )

        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNameOutTextField(
    viewModel: SettingViewModel
){
    var textFieldState by remember{ mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier
        ,
        value = textFieldState,
        onValueChange = {
            textFieldState = it
            viewModel.setUserName(textFieldState) },
        placeholder = {
            Text(
                modifier = Modifier
                ,
                text = stringResource(id = R.string.setting_input_name),
            ) },
        enabled = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun CreateGenderButton(
    viewModel: SettingViewModel
){
    var selectedButtonIndex by remember { mutableStateOf(-1) }

    LazyVerticalGrid(
        modifier = Modifier
            .width(200.dp)
        ,
        columns = GridCells.Fixed(2)
    ){
        itemsIndexed(gender){index, item ->
            val isSelected = selectedButtonIndex == index
            val bgColor = if (isSelected) colorResource(id = R.color.purple_main) else Color.White
            val txtColor = if (isSelected) Color.White else Color.DarkGray
            val borderColor = if(isSelected) colorResource(id = R.color.purple_main) else Color.DarkGray

            Row{
                OutlinedButton(
                    modifier = Modifier,
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(0.5.dp, color = borderColor),
                    colors = ButtonDefaults.buttonColors(containerColor = bgColor),
                    onClick = {
                        viewModel.setUserGender(item)
                        selectedButtonIndex = index // 클릭된 버튼의 인덱스를 저장
                    }
                ) {
                    Text(
                        color = txtColor,
                        text = item,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }


}

@Composable
fun CreateMbtiButton(
    viewModel: SettingViewModel
){
    var mbtiDialogState by remember{ mutableStateOf(false) }
    val userInfo by viewModel.userInfo.collectAsState()
    val btnColor = if(userInfo.mbti != "") colorResource(id = R.color.purple_main) else Color.DarkGray
    val textStatus = if(userInfo.mbti != "") userInfo.mbti else stringResource(id = R.string.setting_mbti_choose)
    val textColor = if(userInfo.mbti != "") colorResource(id = R.color.purple_main) else Color.DarkGray

    OutlinedButton(
        modifier = Modifier
            .wrapContentWidth()
        ,
        onClick = {
            mbtiDialogState = true
        },
        border = BorderStroke(0.5.dp, color = btnColor)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp)
            ,
            text = textStatus,
            color = textColor,
            fontWeight = if(userInfo.mbti != "") FontWeight.Bold else FontWeight.Normal
        )
        
    }
    if(mbtiDialogState){
        MbtiDialog(
            showDialog = { mbtiDialogState = it },
            setMbtiData = { viewModel.setUserMbti(it) }
        )
    }

}

@Composable
fun CreateFavoriteButton(
    viewModel: SettingViewModel
){
    val userInfo by viewModel.userInfo.collectAsState()
    var favoriteDialogState by remember { mutableStateOf(false) }
    val btnColor = if(checkFavorite(userInfo)) colorResource(id = R.color.purple_main) else Color.DarkGray
    val textColor = if(checkFavorite(userInfo)) colorResource(id = R.color.purple_main) else Color.DarkGray
    val textStatus: String =
        if(checkFavorite(userInfo)){
            "${userInfo.firstFavorite ?: ""} ${userInfo.secondFavorite ?: ""} ${userInfo.thirdFavorite ?: ""}"
        }else{
            stringResource(id = R.string.setting_favorite_button_title)
        }


    OutlinedButton(
        onClick = { favoriteDialogState = true },
        border = BorderStroke(0.5.dp, btnColor)
    ) {
        Text(
            text = textStatus,
            color = textColor,
            fontWeight = if(checkFavorite(userInfo)) FontWeight.Bold else FontWeight.Normal
        )
    }
    if(favoriteDialogState){
        FavoriteDialog(
            showDialog = { favoriteDialogState = it },
            setFavoriteData = { viewModel.setUserFavorite(it) })
    }
}

@Composable
fun CreateCompleteButton(
    viewModel: SettingViewModel,
    onComplete: () -> Unit
){
    var checkState by remember { mutableStateOf(false)}
    val userInfo by viewModel.userInfo.collectAsState()

    checkState = checkUserAllData(userInfo)

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 100.dp,
                horizontal = 40.dp
            )
            .height(50.dp)
        ,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_main)),
        enabled = checkState,
        onClick = onComplete
    ) {
        Text(
            text = stringResource(id = R.string.setting_complete),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

private fun checkFavorite(
    userInfo: UserInformation
): Boolean{
    return userInfo.firstFavorite != null ||
            userInfo.secondFavorite != null ||
            userInfo.thirdFavorite != null
}

private fun checkUserAllData(
    userInfo: UserInformation
): Boolean{
    return userInfo.name != "" &&
            checkFavorite(userInfo) &&
            userInfo.mbti != "" &&
            userInfo.gender != ""

}

@Preview(showBackground = true)
@Composable
fun PreviewSettingView(){
    val viewModel = SettingViewModel()
    KausanhakTheme {
        SettingScreen(onComplete = {})
    }
}

private val gender = listOf(
    "남성", "여성"
)
