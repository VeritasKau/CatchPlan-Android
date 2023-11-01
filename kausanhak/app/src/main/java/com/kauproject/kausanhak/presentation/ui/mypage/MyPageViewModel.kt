package com.kauproject.kausanhak.presentation.ui.mypage

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.kauproject.kausanhak.data.remote.service.login.DeleteUserService
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.util.Constants.KAKAO
import com.kauproject.kausanhak.presentation.util.Constants.NAVER
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val deleteUserService: DeleteUserService
): ViewModel() {
    companion object{
        const val TAG = "MyPageViewModel"
    }
    fun getLogOut(){
        viewModelScope.launch {
            userDataRepository.setUserData("userNum", "")
        }
    }

    fun deleteUser(context: Context){
        viewModelScope.launch {
            val platform = userDataRepository.getUserData().platform
            val statusCode = deleteUserService.deleteUser().code()

            if(statusCode == 200){
                when(platform){
                    KAKAO -> { kakaoDelete() }
                    NAVER -> { naverDelete(context) }
                }
                userDataRepository.setUserData("userNum", "")
                userDataRepository.setUserData("token", "")
                userDataRepository.setUserData("first", "")
                userDataRepository.setUserData("second", "")
                userDataRepository.setUserData("third", "")
                Log.d(TAG,"SUCCESS DELETE")
            }else{
                Log.d(TAG, "DELETE ERROR")
            }
        }
    }

    private fun kakaoDelete(){
        UserApiClient.instance.unlink {}
    }

    private fun naverDelete(context: Context){
        NidOAuthLogin().callDeleteTokenApi(context, object: OAuthLoginCallback{
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d(TAG, "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }
            override fun onSuccess() {}
        })
    }
}