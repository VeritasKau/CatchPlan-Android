package com.kauproject.kausanhak.presentation.ui.login

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel(context: Context): ViewModel() {
    private val kakaoLoginManager = KakaoLoginManager(context)
    private val naverLoginManager = NaverLoginManager(context)

    private val _userData = MutableStateFlow(LoginData())
    val userData: StateFlow<LoginData>
        get() = _userData.asStateFlow()

    fun startLogin(platform: String){
        if(platform == "카카오"){
            kakaoLoginManager.startKakaoLogin {
                UserApiClient.instance.me { user, _ ->
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
                        _userData.update { it->
                            it.copy(
                                userId = result.profile?.id.toString(),
                                platform = NAVER
                            )
                        }
                    }
                })

            }
        }
    }

}