package com.kauproject.kausanhak.data.remote.service.recommend

import com.kauproject.kausanhak.data.remote.response.RecommendResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecommendService {
    @GET("/combined_recommendation/")
    suspend fun getRecommend(
        @Query("genre") genre: String,
        @Query("mbti") mbti: String
    ): Response<RecommendResponse>

}