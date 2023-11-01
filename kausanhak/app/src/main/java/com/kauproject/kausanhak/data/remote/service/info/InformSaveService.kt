package com.kauproject.kausanhak.data.remote.service.info

import com.kauproject.kausanhak.data.remote.request.InformSaveRequest
import com.kauproject.kausanhak.data.remote.response.NetworkResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InformSaveService {
    @POST("/api/memberInfo/save")
    suspend fun informSave(
        @Body saveRequest: InformSaveRequest
    ): Response<NetworkResponse>
}