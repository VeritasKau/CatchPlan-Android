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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.ui.theme.KausanhakTheme

private val selectFavorite = ArrayList<String>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteSettingScreen(
    onCompleteButtonClick: () -> Unit
){
    val viewModel = FavoriteSettingViewModel()
    val userFavorite by viewModel.userFavorite.collectAsState()
    var cnt by remember{ mutableStateOf(0) }

    Scaffold(
        modifier = Modifier
            .padding(horizontal = 20.dp)
        ,
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                ,
            ) {
                Text(
                    text = stringResource(id = R.string.favorite_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = stringResource(id = R.string.favorite_sub_title),
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.padding(vertical = 30.dp))

                SetFavoriteButton(viewModel = viewModel){ it->
                    cnt = it
                }

                Spacer(modifier = Modifier.padding(vertical = 30.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 100.dp)
                    ,
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_main)),
                    shape = RoundedCornerShape(15.dp),
                    enabled = cnt > 0,
                    onClick = {
                        viewModel.setFavorite(getFavoriteList(viewModel))
                        onCompleteButtonClick()
                    }
                ){
                    Text(
                        text = stringResource(id = R.string.favorite_complete),
                        fontWeight = FontWeight.Bold
                    )

                }

            }
        }
        
    }
}

private fun getFavoriteList(
    viewModel: FavoriteSettingViewModel
): List<String>{
    val size = selectFavorite.size
    val result = mutableListOf<String>()

    for(i in 0 until 3){
        if(i < size){
            result.add(selectFavorite[i])
        }else{
            result.add("")
        }
    }

    return result
}
@Composable
fun SetFavoriteButton(
    viewModel: FavoriteSettingViewModel,
    onCompleteBtnCallBack: (Int) -> Unit
){
    val selectedList = remember{ mutableStateListOf<Boolean>() }
    selectedList.addAll(List(favoriteData.size){false})
    var cnt = 0

    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ){
        itemsIndexed(favoriteData){index, item ->
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


@Preview(showBackground = true)
@Composable
fun PreviewFavoriteSetting(){
    KausanhakTheme {
        FavoriteSettingScreen(
            onCompleteButtonClick = {}
        )
    }

}