package com.kauproject.kausanhak.data.remote.response


import com.squareup.moshi.Json

data class SignInResponse(
    @field:Json(name = "accessToken")
    val accessToken: String?,
    @field:Json(name = "accessTokenExpireTime")
    val accessTokenExpireTime: String?,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "grantType")
    val grantType: String?,
    @field:Json(name = "refreshToken")
    val refreshToken: String?,
    @field:Json(name = "refreshTokenExpireTime")
    val refreshTokenExpireTime: String?,
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "statusCode")
    val statusCode: Int?,
    @field:Json(name = "transactionTime")
    val transactionTime: String?
)