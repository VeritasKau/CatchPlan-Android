package com.kauproject.kausanhak.data.remote.request

import com.squareup.moshi.Json

data class ChatRequest(
    @Json(name = "content")
    val content: String?
)