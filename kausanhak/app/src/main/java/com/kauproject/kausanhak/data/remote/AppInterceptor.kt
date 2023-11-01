package com.kauproject.kausanhak.data.remote

import android.util.Log
import com.kauproject.kausanhak.presentation.util.ApplicationClass
import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor: Interceptor {
    companion object{
        const val TAG = "AppInterceptor"
    }

    @Throws
    override fun intercept(chain: Interceptor.Chain): Response {
        val authToken = ApplicationClass.authToken
        Log.d(TAG, authToken)
        val request = chain.request().newBuilder()
            .addHeader("Authorization", authToken)
            .build()
        return chain.proceed(request)
    }
}