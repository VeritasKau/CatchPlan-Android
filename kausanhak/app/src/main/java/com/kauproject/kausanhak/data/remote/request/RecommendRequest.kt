package com.kauproject.kausanhak.data.remote.request


import com.squareup.moshi.Json

data class RecommendRequest(
    @Json(name = "unique_user_info")
    val uniqueUserInfo: String?
)