package com.kauproject.kausanhak.presentation.ui.chatbot

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kauproject.kausanhak.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ChatBotViewModel: ViewModel() {
    private val _messageData = MutableStateFlow<List<Message>>(emptyList())
    val messageData = _messageData.asStateFlow()

    init {
        initChat()
    }

    companion object{
        const val TAG = "ChatBotVM"
        const val CHAT_URL = BuildConfig.CHAT_URL
    }
    fun sendChat(content: String){
        _messageData.value = _messageData.value.toMutableList().apply {
            add(Message(content = content, isMe = true))
        }
    }

    private fun initChat(){
        _messageData.value = _messageData.value.toMutableList().apply {
            add(Message(content = "안녕하세요! 무엇을 도와드릴까요?", isMe = false))
        }
    }
}