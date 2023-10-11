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
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.setting.SettingViewModel
import com.kauproject.kausanhak.presentation.ui.setting.favoriteData
import com.kauproject.kausanhak.ui.theme.KausanhakTheme

private val selectFavorite = ArrayList<String>()

@Composable
fun FavoriteDialog(
    showDialog: (Boolean) -> Unit,
    setFavoriteData: (List<String?>) -> Unit
){
    val viewModel: SettingViewModel = viewModel()

    Dialog(onDismissRequest = { showDialog(false) }) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ) {
            FavoriteDialogContent(
                viewModel = viewModel,
                showDialog = showDialog,
                setFavoriteData = setFavoriteData,
            )
        }
    }
}

@Composable
fun FavoriteDialogContent(
    viewModel: SettingViewModel,
    showDialog: (Boolean) -> Unit,
    setFavoriteData: (List<String?>) -> Unit,
){
    val favoriteData by viewModel.userInfo.collectAsState()
    var cnt by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 15.dp),
            text = stringResource(id = R.string.setting_favorite_title)
        )
        Text(
            modifier = Modifier
                .padding(start = 15.dp),
            text = stringResource(id = R.string.setting_favorite_sub_title),
            color = Color.LightGray,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.padding(10.dp))
        SetFavoriteButton(
            viewModel = viewModel,
            onCompleteBtnCallBack = { cnt = it }
        )
        Spacer(modifier = Modifier.padding(10.dp))
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
                        enabled = cnt != 0
                    ){
                        setFavoriteData(getFavoriteList())
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
fun SetFavoriteButton(
    viewModel: SettingViewModel,
    onCompleteBtnCallBack: (Int) -> Unit
){
    val selectedList = remember{ mutableStateListOf<Boolean>() }
    selectedList.addAll(List(favoriteData.size){false})
    var cnt = 0

    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ){
        itemsIndexed(favoriteData){ index, item ->
            val selected = selectedList[index]
            val bgColor = if (selected) colorResource(id = R.color.purple_main) else Color.White
            val txtColor = if (selected) Color.White else colorResource(id = R.color.purple_main)

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
                        if(!selectedList[index] && cnt != 3){
                            selectedList[index] = !selected
                            selectFavorite.add(item)
                            cnt++
                        }else if(selectedList[index]){
                            selectedList[index] = !selected
                            selectFavorite.remove(item)
                            cnt--
                        }
                        onCompleteBtnCallBack(cnt)
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

private fun getFavoriteList(): List<String?>{
    val size = selectFavorite.size
    val result = mutableListOf<String?>()

    for(i in 0 until 3){
        if(i < size){
            result.add(selectFavorite[i])
        }else{
            result.add(null)
        }
    }
    selectFavorite.clear()
    return result
}

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteDialog(){
    KausanhakTheme {
        FavoriteDialog(showDialog = {}, setFavoriteData = {})
    }
}