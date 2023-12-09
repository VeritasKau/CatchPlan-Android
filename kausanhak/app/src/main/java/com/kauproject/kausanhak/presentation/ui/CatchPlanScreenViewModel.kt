package com.kauproject.kausanhak.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.remote.service.login.CheckMemberService
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatchPlanScreenViewModel @Inject constructor(
    private val checkMemberService: CheckMemberService,
    private val userDataRepository: UserDataRepository
): ViewModel() {
    private val _isMember = MutableStateFlow<Boolean?>(null)
    val isMember = _isMember.asStateFlow()

    init {
        checkMember()
    }

    private fun checkMember(){
        viewModelScope.launch {
            val userId = userDataRepository.getUserData().num

            val response = checkMemberService.checkMember(userId)
            val statusCode = response.code()

            if(statusCode == 200){
                response.body()?.let {
                    _isMember.value = it
                }
            }
        }
    }
}