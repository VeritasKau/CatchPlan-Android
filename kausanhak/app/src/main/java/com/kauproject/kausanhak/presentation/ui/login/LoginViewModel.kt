package com.kauproject.kausanhak.presentation.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.kauproject.kausanhak.data.remote.request.SignInRequest
import com.kauproject.kausanhak.data.remote.service.info.GetUserInfoService
import com.kauproject.kausanhak.data.remote.service.login.CheckMemberService
import com.kauproject.kausanhak.data.remote.service.login.SignInService
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.ui.login.manager.KakaoLoginManager
import com.kauproject.kausanhak.presentation.ui.login.manager.NaverLoginManager
import com.kauproject.kausanhak.presentation.util.Constants.KAKAO
import com.kauproject.kausanhak.presentation.util.Constants.NAVER
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    context: Context,
    private val userDataRepository: UserDataRepository,
    private val signInService: SignInService,
    private val checkMemberService: CheckMemberService,
    private val getUserInfoService: GetUserInfoService
): ViewModel() {

    private val kakaoLoginManager = KakaoLoginManager(context)
    private val naverLoginManager = NaverLoginManager(context)

    private val _isMember = MutableStateFlow<Boolean?>(null)
    val isMember: StateFlow<Boolean?>
        get() = _isMember.asStateFlow()

    fun startLogin(platform: String){
        if(platform == "카카오"){
            kakaoLoginManager.startKakaoLogin {
                UserApiClient.instance.me { user, _ ->
                    viewModelScope.launch {
                        userDataRepository.setUserData("userNum", user?.id.toString())
                        userDataRepository.setUserData("platform", KAKAO)

                        val response = checkMemberService.checkMember(user?.id.toString())
                        val statusCode = response.code()

                        if(statusCode == 200) {
                            _isMember.value = response.body()!!
                            Log.d("TEST CHECK", "${response.body()}")
                            if (response.body()!!) {
                                getUserInfo(user?.id.toString(), userDataRepository)
                            }
                            getToken(user?.id.toString(), userDataRepository)
                        }
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
                            userDataRepository.setUserData("platform", NAVER)
                            val response = checkMemberService.checkMember(result.profile?.id.toString())
                            val statusCode = response.code()

                            if(statusCode == 200){
                                _isMember.value = response.body()!!
                                Log.d("TEST CHECK", "${response.body()}")
                                if(response.body()!!){
                                    getUserInfo(userId, userDataRepository)
                                }
                                getToken(userId, userDataRepository)
                            }
                        }
                    }
                })

            }
        }
    }

    private fun getToken(
        userId: String,
        userDataRepository: UserDataRepository
    ){
        if(userId != "" && userId.isNotBlank()){
            viewModelScope.launch {
                val request = SignInRequest(
                    uniqueUserInfo = userId
                )
                val signInResponse = signInService.signInInform(request)
                val token = signInResponse.body()?.accessToken
                val statusCode = signInResponse.code()

                if(statusCode == 200){
                    userDataRepository.setUserData("token", token!!)
                }else{
                    Log.d("TokenError", "$statusCode")
                }
            }
        }
    }

    private fun getUserInfo(
        userId: String,
        userDataRepository: UserDataRepository
    ){
        viewModelScope.launch(Dispatchers.IO) {
            if(userDataRepository.getUserData().num == ""){
                val response = getUserInfoService.getUserInfo(userId)
                val statusCode = response.code()

                if(statusCode == 200){
                    response.body()?.let {
                        userDataRepository.setUserData("userNum", userId)
                        userDataRepository.setUserData("name", it.name!!)
                        userDataRepository.setUserData("gender", it.sex!!)
                        userDataRepository.setUserData("mbti", it.mbti!!)
                        userDataRepository.setUserData("first", mapperToFav(it.genre1!!))
                        userDataRepository.setUserData("second", mapperToFav(it.genre2!!))
                        userDataRepository.setUserData("third", mapperToFav(it.genre3!!))
                    }
                }

            }
        }

    }

}

private fun mapperToFav(fav: String): String {
    return when(fav) {
        "musical" -> "뮤지컬" "concert" -> "콘서트" "drama" -> "연극"
        "classic" -> "클래식" "korea" -> "지역행사" "camping" -> "레저/캠핑"
        "exhibition" -> "전시회/박물관" "kids" -> "아동/가족"
        else -> ""
    }
}