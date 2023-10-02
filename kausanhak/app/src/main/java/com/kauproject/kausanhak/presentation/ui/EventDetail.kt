package com.kauproject.kausanhak.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.ui.theme.KausanhakTheme

@Composable
fun EventDetailView(
    image: String,
    detailImage: String,
    url: String,
    date: String,
    pressOnBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        NetworkImage(
            modifier = Modifier
                .fillMaxSize()
            ,
            url = image
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(18.dp)
            ,
            text = date
        )
        NetworkImage(
            modifier = Modifier
                .fillMaxSize()
            ,
            url = detailImage
        )
    }


}

@Preview(showBackground = true)
@Composable
fun DetailPreview(){
    val mock = Event.mock()
    KausanhakTheme {
        EventDetailView(
            image = mock.image,
            detailImage = mock.detailImage,
            url = mock.url,
            date = mock.date)
    }
}