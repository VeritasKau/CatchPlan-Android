package com.kauproject.kausanhak.presentation.ui.upload

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.data.remote.request.PostPromotionRequest
import com.kauproject.kausanhak.data.remote.service.promotion.PostPromotionService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.presentation.util.UriUtil
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Multipart
import java.io.File
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class UpLoadFormViewModel @Inject constructor(
    private val postPromotionService: PostPromotionService
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

    //url
    private val _url = mutableStateOf<String?>(null)
    val url: String?
        get() = _url.value

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

    fun onPostPromotion(
        context: Context
    ): Flow<State<Int>> = flow{
        emit(State.Loading)

        val file = UriUtil.toFile(context, mainImageUri!!)
        val requestImage = file.asRequestBody("image/*".toMediaTypeOrNull())

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("text", title)
            .addFormDataPart("place", place)
            .addFormDataPart("url", "")
            .addFormDataPart("detail2", content)
            .addFormDataPart("duration", "$startDate~$endDate")
            .addFormDataPart(
                "image", file.name, requestImage
            )
            .addFormDataPart(
                "detail", file.name, requestImage
            )
            .build()


        val response = postPromotionService.postPromotion(
            body = body
        )

        val statusCode = response.code()

        if(statusCode == 200){
            emit(State.Success(statusCode))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }


    // value 관리
    fun onWriteContent(title: String, content: String){
        _title.value = title
        _content.value = content
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onUpLoadPoster(uri: Uri?){
        _mainImageUri.value = uri
        Log.d("TEST", "$uri")
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onWritePlace(place: String){
        _place.value = place
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onWriteURL(url: String?){
        url?.let {
            _url.value = it
        }
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