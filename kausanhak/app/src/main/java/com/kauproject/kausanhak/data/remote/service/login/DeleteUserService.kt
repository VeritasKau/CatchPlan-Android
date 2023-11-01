package com.kauproject.kausanhak.data.remote.service.login

import com.kauproject.kausanhak.data.remote.response.NetworkResponse
import retrofit2.Response
import retrofit2.http.DELETE

interface DeleteUserService {
    @DELETE("/api/memberInfo/delete")
    suspend fun deleteUser(): Response<NetworkResponse>
}