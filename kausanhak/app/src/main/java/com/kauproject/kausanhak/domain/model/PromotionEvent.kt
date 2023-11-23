package com.kauproject.kausanhak.domain.model

import android.net.Uri

data class PromotionEvent(
    val title: String,
    val content: String,
    val startDate: String,
    val endDate: String,
    val image: Uri?,
    val place: String
)
