package com.kauproject.kausanhak.data.remote.service.info

import com.kauproject.kausanhak.data.remote.response.GetUserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetUserInfoService {
    @GET("/api/memberInfo/getMemberInfo")
    suspend fun getUserInfo(
        @Query("uniqueUserInfo") uniqueUserInfo: String
    ): Response<GetUserInfoResponse>
}