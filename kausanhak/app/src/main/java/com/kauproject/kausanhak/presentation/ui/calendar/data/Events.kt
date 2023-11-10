package com.kauproject.kausanhak.presentation.ui.calendar.data

import androidx.annotation.ColorRes
import java.time.LocalDateTime

data class Events(
    val id: Int = 0,
    val date: LocalDateTime = LocalDateTime.now(),
    val name: String = "",
    val place: String = "",
    val image: String = "",
    @ColorRes val color: Int = 0
)