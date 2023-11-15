package com.kauproject.kausanhak.data.remote.request


import com.squareup.moshi.Json

data class ScrapRequest(
    @field:Json(name = "eventId")
    val eventId: Int?,
    @field:Json(name = "uniqueUserInfo")
    val uniqueUserInfo: String?
)