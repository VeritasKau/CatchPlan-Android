package com.kauproject.kausanhak.data.remote.response


import com.squareup.moshi.Json

data class SignInResponse(
    @field:Json(name = "accessToken")
    val accessToken: String?,
    @field:Json(name = "accessTokenExpireTime")
    val accessTokenExpireTime: String?,
    @field:Json(name = "grantType")
    val grantType: String?,
    @field:Json(name = "refreshToken")
    val refreshToken: String?,
    @field:Json(name = "refreshTokenExpireTime")
    val refreshTokenExpireTime: String?
)