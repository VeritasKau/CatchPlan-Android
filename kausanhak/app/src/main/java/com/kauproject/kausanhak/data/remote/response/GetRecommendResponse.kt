package com.kauproject.kausanhak.data.remote.response


import com.squareup.moshi.Json

data class GetRecommendResponse(
    @Json(name = "recommended_event_id")
    val recommendedEventId: List<Int?>?
)