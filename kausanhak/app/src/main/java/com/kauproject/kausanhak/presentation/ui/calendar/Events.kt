package com.kauproject.kausanhak.presentation.ui.calendar

import java.time.LocalDateTime

data class Events(
    val time: LocalDateTime,
)
fun eventsList(): List<Events> = buildList {

}