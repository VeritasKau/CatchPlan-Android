package com.kauproject.kausanhak.data.remote.response


import com.squareup.moshi.Json

data class RecommendResponse(
    @Json(name = "combined_recommendations")
    val combinedRecommendations: List<CombinedRecommendation?>?,
    @Json(name = "combined_weight")
    val combinedWeight: Double?,
    @Json(name = "genre_weight")
    val genreWeight: Double?,
    @Json(name = "mbti_weight")
    val mbtiWeight: Double?,
    @Json(name = "user_genre")
    val userGenre: String?,
    @Json(name = "user_mbti")
    val userMbti: String?
)

data class CombinedRecommendation(
    @Json(name = "count")
    val count: Int?,
    @Json(name = "event_ids")
    val eventIds: List<Int?>?,
    @Json(name = "genre")
    val genre: String?
)