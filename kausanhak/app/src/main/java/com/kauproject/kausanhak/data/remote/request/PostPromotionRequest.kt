package com.kauproject.kausanhak.data.remote.request


import com.squareup.moshi.Json

data class PostPromotionRequest(
    @Json(name = "detail2")
    val detail2: String?,
    /*@Json(name = "duration")
    val duration: String?,*/
    @Json(name = "place")
    val place: String?,
    @Json(name = "text")
    val text: String?,
    @Json(name = "url")
    val url: String?
)