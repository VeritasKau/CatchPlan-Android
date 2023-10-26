package com.kauproject.kausanhak.data.remote.request


import com.squareup.moshi.Json

data class SignInRequest(
    @field:Json(name = "uniqueUserInfo")
    val uniqueUserInfo: String?
)