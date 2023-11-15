package com.kauproject.kausanhak.data.remote.response


import com.squareup.moshi.Json

data class GetUserInfoResponse(
    @field:Json(name = "description")
    val description: String? = "",
    @field:Json(name = "genre1")
    val genre1: String? = "",
    @field:Json(name = "genre2")
    val genre2: String? = "",
    @field:Json(name = "genre3")
    val genre3: String? = "",
    @field:Json(name = "mbti")
    val mbti: String? = "",
    @field:Json(name = "name")
    val name: String? = "",
    @field:Json(name = "sex")
    val sex: String? = "",
    @field:Json(name = "status")
    val status: String? = "",
    @field:Json(name = "statusCode")
    val statusCode: Int? = 0,
    @field:Json(name = "transactionTime")
    val transactionTime: String? = ""
)