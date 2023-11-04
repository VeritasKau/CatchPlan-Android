package com.kauproject.kausanhak.presentation.ui.calendar

import androidx.annotation.ColorRes
import java.time.LocalDateTime

data class Events(
    val id: Int,
    val date: LocalDateTime,
    val name: String,
    val place: String,
    val image: String,
    @ColorRes val color: Int
)
fun eventsList(): List<Events> = buildList {


}