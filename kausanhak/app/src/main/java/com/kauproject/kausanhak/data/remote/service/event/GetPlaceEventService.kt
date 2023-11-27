package com.kauproject.kausanhak.data.remote.service.event

import com.kauproject.kausanhak.data.remote.response.GetPlaceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetPlaceEventService {
    @GET("/api/events/place")
    suspend fun getPlaceEvent(
        @Query("place") place: String
    ):Response<List<GetPlaceResponse>>
}