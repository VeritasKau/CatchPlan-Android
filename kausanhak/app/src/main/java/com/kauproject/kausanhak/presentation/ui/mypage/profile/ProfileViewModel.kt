package com.kauproject.kausanhak.presentation.ui.mypage.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.remote.response.GetUserInfoResponse
import com.kauproject.kausanhak.data.remote.service.info.GetUserInfoService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.UserData
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userInfoService: GetUserInfoService,
    private val userDataRepository: UserDataRepository
): ViewModel() {
    fun initUserData(): Flow<State<GetUserInfoResponse>> = flow {
        emit(State.Loading)

        val userNum = userDataRepository.getUserData().num
        val response = userInfoService.getUserInfo(userNum)
        val statusCode = response.code()

        if(statusCode == 200){
            emit(State.Success(response.body()!!))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

}