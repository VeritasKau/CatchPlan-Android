package com.kauproject.kausanhak.presentation.ui.upload

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class UpLoadFormViewModel @Inject constructor(

): ViewModel() {

    companion object{
        const val START = "시작날짜 선택"
        const val END = "종료날짜 선택"
    }

    // 업로드 단계 순서 List
    private val upLoadForm: List<UpLoadStep> = listOf(
        UpLoadStep.CONTENT,
        UpLoadStep.IMAGE,
        UpLoadStep.DATE_PICK,
        UpLoadStep.PREVIEW
    )

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value

    private val _upLoadFormScreenData = mutableStateOf(createUpLoadFormData())
    val upLoadFormScreenData: UpLoadFormScreenData
        get() = _upLoadFormScreenData.value

    /* 각 단계마다 상태 설정*/
    // 행사 제목
    private val _title = mutableStateOf("")
    val title: String
        get() = _title.value

    // 행사 내용
    private val _content = mutableStateOf("")
    val content: String
        get() = _content.value

    // 이미지 uri
    private val _mainImageUri = mutableStateOf<Uri?>(null)
    val mainImageUri: Uri?
        get() = _mainImageUri.value

    // 일정 등록
    private val _startDate = mutableStateOf<String>(START)
    val startDate: String
        get() = _startDate.value

    private val _endDate = mutableStateOf<String>(END)
    val endDate: String
        get() = _endDate.value

    // 장소 작성
    private val _place = mutableStateOf<String>("")
    val place: String
        get() = _place.value

    // 미리보기 확인
    private val _complete = mutableStateOf<Boolean?>(null)
    val complete: Boolean?
        get() = _complete.value



    private var stepIndex = 0 // 현재 단계

    // 업로드 단계 별 로직
    fun onBackPressed(): Boolean{
        if(stepIndex == 0){
            return false
        }
        changeStepIndex(stepIndex - 1)
        return true
    }

    fun onPreviousPressed(){
        if(stepIndex == 0){
            throw IllegalStateException("onPreviousPressed when stepIndex 0")
        }
        changeStepIndex(stepIndex - 1)
    }

    fun onNextPressed(){
        changeStepIndex(stepIndex + 1)
    }

    fun onCompletePressed(onComplete: () -> Unit){
        onComplete()
    }

    // value 관리
    fun onWriteContent(title: String, content: String){
        _title.value = title
        _content.value = content
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onUpLoadPoster(uri: Uri?){
        _mainImageUri.value = uri
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onWritePlace(place: String){
        _place.value = place
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onSelectedDate(start: String, end: String){
        _startDate.value = start
        _endDate.value = end
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onComplete(){
        _complete.value = true
        _isNextEnabled.value = getIsNextEnabled()
    }


    private fun changeStepIndex(newStepIndex: Int){
        stepIndex = newStepIndex
        _isNextEnabled.value = getIsNextEnabled()
        _upLoadFormScreenData.value = createUpLoadFormData()
    }

    private fun getIsNextEnabled(): Boolean{
        return when(upLoadForm[stepIndex]){
            UpLoadStep.CONTENT -> { _title.value != "" && _content.value != "" }
            UpLoadStep.IMAGE -> { _mainImageUri.value != null }
            UpLoadStep.DATE_PICK -> { _startDate.value != START && _endDate.value != END && _place.value != "" }
            UpLoadStep.PREVIEW -> { _complete.value != null }
        }
    }

    private fun createUpLoadFormData(): UpLoadFormScreenData {
        return UpLoadFormScreenData(
            stepIndex = stepIndex,
            stepCount = upLoadForm.size,
            shouldShowPreviousButton = stepIndex > 0,
            shouldShowCompleteButton = stepIndex == upLoadForm.size-1,
            upLoadStep = upLoadForm[stepIndex]
        )

    }

    enum class UpLoadStep{
        CONTENT,
        IMAGE,
        DATE_PICK,
        PREVIEW
    }

}
data class UpLoadFormScreenData(
    val stepIndex: Int,
    val stepCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowCompleteButton: Boolean,
    val upLoadStep: UpLoadFormViewModel.UpLoadStep
)