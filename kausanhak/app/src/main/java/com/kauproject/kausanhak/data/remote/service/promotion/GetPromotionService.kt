package com.kauproject.kausanhak.data.remote.service.promotion

import com.kauproject.kausanhak.data.remote.response.GetPromotionResponse
import retrofit2.Response
import retrofit2.http.GET

interface GetPromotionService {
    @GET("/api/small_events")
    suspend fun getPromotion(): Response<List<GetPromotionResponse>>
}