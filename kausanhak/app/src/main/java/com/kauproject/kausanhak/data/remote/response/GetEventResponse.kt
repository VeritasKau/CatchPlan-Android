package com.kauproject.kausanhak.data.remote.response


import com.squareup.moshi.Json
data class GetEventResponse(
    @field:Json(name = "detail")
    val detail: String?,
    @field:Json(name = "detail2")
    val detail2: String?,
    @field:Json(name = "dtype")
    val dtype: String?,
    @field:Json(name = "duration")
    val duration: String?,
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "image")
    val image: String?,
    @field:Json(name = "place")
    val place: String?,
    @field:Json(name = "status")
    val status: Int?,
    @field:Json(name = "text")
    val text: String?,
    @field:Json(name = "url")
    val url: String?
)