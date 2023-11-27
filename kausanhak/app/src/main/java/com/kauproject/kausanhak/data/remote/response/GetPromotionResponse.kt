package com.kauproject.kausanhak.data.remote.response


import com.squareup.moshi.Json

data class GetPromotionResponse(
    @Json(name = "detail")
    val detail: String?,
    @Json(name = "detail2")
    val detail2: String?,
    @Json(name = "duration")
    val duration: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "place")
    val place: String?,
    @Json(name = "status")
    val status: Boolean?,
    @Json(name = "text")
    val text: String?,
    @Json(name = "url")
    val url: String?
)