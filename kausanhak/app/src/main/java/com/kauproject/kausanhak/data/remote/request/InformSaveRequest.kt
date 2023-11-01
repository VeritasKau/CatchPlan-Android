package com.kauproject.kausanhak.data.remote.request


import com.squareup.moshi.Json

data class InformSaveRequest(
    @field:Json(name = "genre1")
    val genre1: String?,
    @field:Json(name = "genre2")
    val genre2: String?,
    @field:Json(name = "genre3")
    val genre3: String?,
    @field:Json(name = "mbti")
    val mbti: String?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "sex")
    val sex: String?
)