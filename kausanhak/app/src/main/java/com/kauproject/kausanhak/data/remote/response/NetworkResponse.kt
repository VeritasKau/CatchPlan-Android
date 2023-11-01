package com.kauproject.kausanhak.data.remote.response


import com.squareup.moshi.Json

data class NetworkResponse(
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "statusCode")
    val statusCode: Int?,
    @field:Json(name = "transaction_time")
    val transactionTime: String?
)