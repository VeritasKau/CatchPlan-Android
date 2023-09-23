package com.kauproject.kausanhak.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.ui.theme.KausanhakTheme

fun EventDetail(
    eventId: Int,
    pressOnBack: () -> Unit = {}
) {
    val events = getMockList()
}

@Composable
private fun EventDetailBody(
    event: Event,
    pressOnBack: () -> Unit
){

}