package com.kauproject.kausanhak.presentation.util

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kauproject.kausanhak.BuildConfig
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class ApplicationClass: Application() {
    @Inject
    lateinit var userDataRepository: UserDataRepository

    companion object{
        var authToken: String = ""
        const val TAG = "ApplicationContext"
    }
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)

        CoroutineScope(Dispatchers.IO).launch {
            authToken = userDataRepository.getUserData().token ?: ""
            Log.d(TAG, "코루틴scope 토큰:$authToken")
        }

        userDataRepository.getTokenData().observeForever{
            Log.d(TAG, "옵저버감지 토큰:$it")
            authToken = it ?: ""
        }
    }

}