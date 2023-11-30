package com.kauproject.kausanhak.data.remote.service.recommend

import com.kauproject.kausanhak.data.remote.request.RecommendRequest
import com.kauproject.kausanhak.data.remote.response.GetRecommendResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface RecommendService {
    @POST("/recommend")
    suspend fun getRecommend(
        @Body uniqueID: RecommendRequest,
    ): Response<GetRecommendResponse>

}