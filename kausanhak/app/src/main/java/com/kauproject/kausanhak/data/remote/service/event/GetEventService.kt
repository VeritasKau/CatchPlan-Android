package com.kauproject.kausanhak.data.remote.service.event

import com.kauproject.kausanhak.data.remote.response.GetEventResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetEventService {
    @GET("/api/events/dtype")
    suspend fun getEvent(
        @Query("dtype") dtype: String
    ): Response<List<GetEventResponse>>
}