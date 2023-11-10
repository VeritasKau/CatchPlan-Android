package com.kauproject.kausanhak.presentation.ui.calendar

import androidx.annotation.ColorRes
import java.time.LocalDateTime

data class Memo(
    val no: Int = 0,
    val date: LocalDateTime = LocalDateTime.now(),
    val content: String = "",
    @ColorRes val color: Int = 0
)
