package com.kauproject.kausanhak.data.remote.service.login

import com.kauproject.kausanhak.data.remote.request.SignInRequest
import com.kauproject.kausanhak.data.remote.response.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("/api/token/generate")
    suspend fun signInInform(
        @Body signInRequest: SignInRequest
    ): Response<SignInResponse>
}