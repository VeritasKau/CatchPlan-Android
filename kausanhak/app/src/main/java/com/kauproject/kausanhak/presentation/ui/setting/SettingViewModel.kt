package com.kauproject.kausanhak.presentation.ui.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.remote.request.InformSaveRequest
import com.kauproject.kausanhak.data.remote.service.info.InformSaveService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.UserData
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
): ViewModel() {

    companion object{
        const val TAG = "SettingVM"
    }

    @Inject
    lateinit var informSaveService: InformSaveService

    private val _userInfo = MutableStateFlow(UserData())
    val userInfo: StateFlow<UserData>
        get() = _userInfo.asStateFlow()

    private val _choiceMbti = MutableStateFlow(ChoiceMbti())
    val choiceMbti: StateFlow<ChoiceMbti>
        get() = _choiceMbti.asStateFlow()

    fun setUserName(name: String){
        _userInfo.update { it->
            it.copy(
                name = name
            )
        }
    }

    fun setUserGender(gender: String){
        _userInfo.update { it->
            it.copy(
                gender = gender
            )
        }
    }

    fun setUserMbti(mbti: String){
        _userInfo.update { it->
            it.copy(
                mbti = mbti
            )
        }
    }

    // 각 항목별로 클릭 데이터 업데이트
    fun setFirstMbti(data: String){
        _choiceMbti.update {
            it.copy(
                first = data
            )
        }
    }
    fun setSecondMbti(data: String){
        _choiceMbti.update {
            it.copy(
                second = data
            )
        }
    }
    fun setThirdMbti(data: String){
        _choiceMbti.update {
            it.copy(
                third = data
            )
        }
    }
    fun setLastMbti(data: String){
        _choiceMbti.update {
            it.copy(
                last = data
            )
        }
    }

    fun setUserFavorite(favoriteList: List<String>){
        _userInfo.update {
            it.copy(
                firstFavorite = favoriteList[0],
                secondFavorite = favoriteList[1],
                thirdFavorite = favoriteList[2]
            )
        }
    }

    fun completeUserData(): Flow<State<Int>> = flow {
        emit(State.Loading)

        val request = InformSaveRequest(
            genre1 = userInfo.value.firstFavorite,
            genre2 = userInfo.value.secondFavorite,
            genre3 = userInfo.value.thirdFavorite,
            mbti = userInfo.value.mbti,
            name = userInfo.value.name,
            sex = userInfo.value.gender
        )
        val response = informSaveService.informSave(request)
        val statusCode = response.code()

        Log.d(TAG, "statusCode:$statusCode")
        if(statusCode == 200){
            emit(State.Success(statusCode))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    fun saveUserData(){
        viewModelScope.launch {
            userDataRepository.setUserData("name", userInfo.value.name)
            userDataRepository.setUserData("gender", userInfo.value.gender)
            userDataRepository.setUserData("mbti", userInfo.value.mbti)
            userDataRepository.setUserData("first", userInfo.value.firstFavorite)
            userDataRepository.setUserData("second", userInfo.value.secondFavorite)
            userDataRepository.setUserData("third", userInfo.value.thirdFavorite)
        }
    }
}


data class ChoiceMbti(
    val first: String = "",
    val second: String = "",
    val third: String = "",
    val last: String = ""
)
