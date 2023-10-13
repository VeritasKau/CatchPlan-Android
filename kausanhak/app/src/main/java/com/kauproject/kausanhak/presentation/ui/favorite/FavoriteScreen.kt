package com.kauproject.kausanhak.presentation.ui.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.ui.theme.KausanhakTheme

@Composable
fun FavoriteScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.favorite_title))
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteScreen(){
    KausanhakTheme {
        FavoriteScreen()
    }
}