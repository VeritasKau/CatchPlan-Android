package com.kauproject.kausanhak.data.remote.service.login

import com.kauproject.kausanhak.data.remote.request.SignInRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface CheckMemberService {
    @GET("/api/memberInfo/checkMember")
    suspend fun checkMember(
        @Query("uniqueUserInfo") uniqueUserInfo: String
    ): Response<Boolean>
}