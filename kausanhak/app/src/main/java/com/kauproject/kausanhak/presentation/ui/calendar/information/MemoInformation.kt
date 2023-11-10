package com.kauproject.kausanhak.presentation.ui.calendar.information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.ui.calendar.CalendarScreenViewModel
import com.kauproject.kausanhak.presentation.ui.calendar.data.Memo
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import com.kauproject.kausanhak.presentation.util.clickable

@Composable
fun LazyItemScope.MemoInformation(
    memo: Memo,
    viewModel: CalendarScreenViewModel
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        ,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(20.dp)
            ,
            painter = painterResource(id = R.drawable.ic_vertical_bar_purple),
            contentDescription = null)
        Text(
            modifier = Modifier,
            text = memo.content,
            fontWeight = FontWeight.Bold
        )


    }

}

@Composable
private fun test(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(20.dp)
            ,
            painter = painterResource(id = R.drawable.ic_vertical_bar_lavendar),
            contentDescription = null)
        Text(
            modifier = Modifier,
            text = "물사러가기",
            fontWeight = FontWeight.Bold
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun preview(){
    KausanhakTheme {
        test()
    }
}