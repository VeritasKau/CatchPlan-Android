package com.kauproject.kausanhak.data.remote.service.promotion

import com.kauproject.kausanhak.data.remote.request.PostPromotionRequest
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface PostPromotionService {
    @Headers("Accept: application/json")
    @POST("/api/small_events/create")
    suspend fun postPromotion(
        @Body body: RequestBody
    ): Response<ResponseBody>
}