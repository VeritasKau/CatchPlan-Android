package com.kauproject.kausanhak.data.remote.service.scrap

import com.kauproject.kausanhak.data.remote.request.ScrapRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ScrapSignService {
    @POST("/api/scrap/save")
    suspend fun scrapSign(
        @Body scrapRequest: ScrapRequest
    ): Response<ResponseBody>
}