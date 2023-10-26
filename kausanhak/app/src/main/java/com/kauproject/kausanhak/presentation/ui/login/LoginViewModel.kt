package com.kauproject.kausanhak.presentation.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import com.kauproject.kausanhak.data.remote.request.SignInRequest
import com.kauproject.kausanhak.data.remote.service.login.SignInService
import com.kauproject.kausanhak.domain.model.Dummy
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.ui.login.manager.KakaoLoginManager
import com.kauproject.kausanhak.presentation.ui.login.manager.NaverLoginManager
import com.kauproject.kausanhak.presentation.util.Constants.KAKAO
import com.kauproject.kausanhak.presentation.util.Constants.NAVER
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel(
    context: Context,
    userDataRepository: UserDataRepository,
    signInService: SignInService
): ViewModel() {

    private val kakaoLoginManager = KakaoLoginManager(context)
    private val naverLoginManager = NaverLoginManager(context)
    private val userDataRepository = userDataRepository
    private val signInService = signInService

    private val _userData = MutableStateFlow(LoginData())
    val userData: StateFlow<LoginData>
        get() = _userData.asStateFlow()

    fun startLogin(platform: String){
        if(platform == "카카오"){
            kakaoLoginManager.startKakaoLogin {
                UserApiClient.instance.me { user, _ ->
                    viewModelScope.launch {
                        userDataRepository.setUserData("userNum", user?.id.toString())
                    }
                    _userData.update { it->
                        it.copy(
                            userId = user?.id.toString(),
                            platform = KAKAO
                        )
                    }
                }
            }
        }else{
            naverLoginManager.startLogin {
                NidOAuthLogin().callProfileApi(object: NidProfileCallback<NidProfileResponse>{
                    override fun onError(errorCode: Int, message: String) {}
                    override fun onFailure(httpStatus: Int, message: String) {}
                    override fun onSuccess(result: NidProfileResponse) {
                        val userId = result.profile?.id.toString()
                        viewModelScope.launch {
                            userDataRepository.setUserData("userNum", result.profile?.id.toString())
                        }
                        _userData.update { it->
                            it.copy(
                                userId = userId,
                                platform = NAVER
                            )
                        }
                        viewModelScope.launch {
                            val request = SignInRequest(
                                uniqueUserInfo = userId
                            )
                            val signInResponse = signInService.signInInform(request)
                            val response = signInResponse.body()
                            Log.d("TEST API", "token:${response?.accessToken}")
                        }
                    }
                })

            }
        }
    }

    fun getUserId(): LiveData<Dummy>{
        return liveData {
            emit(userDataRepository.getUserData())
        }

    }

}