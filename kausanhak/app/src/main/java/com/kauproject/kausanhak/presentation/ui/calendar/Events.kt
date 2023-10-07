package com.kauproject.kausanhak.presentation.ui.calendar

import androidx.annotation.ColorRes
import java.time.LocalDateTime

data class Events(
    val time: LocalDateTime,
    @ColorRes val color: Int
)
fun eventsList(): List<Events> = buildList {

}