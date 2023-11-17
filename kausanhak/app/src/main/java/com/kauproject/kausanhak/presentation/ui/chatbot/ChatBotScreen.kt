package com.kauproject.kausanhak.presentation.ui.chatbot

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.presentation.anim.lottieanimation.LottieChatAnimation
import com.kauproject.kausanhak.presentation.util.clickable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


@Composable
fun ChatBotScreen(
    navController: NavHostController
){
    val viewModel: ChatBotViewModel = hiltViewModel()
    val messageData by viewModel.messageData.collectAsState()
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var scrollIsLateState by remember { mutableStateOf(false) }

    val cantScrollForward = !scrollState.canScrollForward // 더이상 스크롤 불가

    LaunchedEffect(cantScrollForward){
        if(cantScrollForward) scrollIsLateState = true
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
        ,
        topBar = {
            chatTopBar(
            backPress = { navController.navigateUp() }
        ) },
        bottomBar = { InputChat(viewModel = viewModel) }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .background(colorResource(id = R.color.lavender_4))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                ,
                state = scrollState
            ){
                itemsIndexed(items = messageData) { _, message ->
                    message.isMe?.let {
                        if(it){
                            ChatBubble(viewModel = viewModel, content = message.content ?: "")
                        }else{
                            ChatBotBubble(content = message.content ?: "", isInit = true)
                        }
                    }
                }
                scope.launch {
                    if(scrollIsLateState) scrollState.animateScrollToItem(index = messageData.size)
                }
            }
        }
    }
}

@Composable
private fun chatTopBar(
    backPress: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .height(50.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                ),
            onClick = backPress
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.detail_back),
                tint = colorResource(id = R.color.purple_main)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 36.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ChatBot",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.purple_main),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun InputChat(
    viewModel: ChatBotViewModel
){
    var textFieldState by remember{ mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .height(60.dp)
            .background(Color.White)
        ,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.85f)
                    .wrapContentHeight()
                    .padding(start = 10.dp)
                    .padding(vertical = 3.dp)
                    .background(
                        color = colorResource(id = R.color.gray_1),
                        shape = RoundedCornerShape(8.dp)
                    )
            ){
                TextField(
                    modifier = Modifier
                    ,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            viewModel.sendChat(textFieldState)
                            textFieldState = ""
                        }
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.chat_hint),
                            color = Color.LightGray
                        ) },
                    value = textFieldState,
                    onValueChange = { textFieldState = it },
                    shape = RoundedCornerShape(5.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                    ),
                    maxLines = 4
                )
            }
            Image(
                modifier = Modifier
                    .size(45.dp)
                    .weight(0.15f)
                    .padding(5.dp)
                    .clickable {
                        viewModel.sendChat(textFieldState)
                        textFieldState = ""
                    }
                    .wrapContentSize()
                ,
                painter = painterResource(id = R.drawable.ic_send),
                contentDescription = null
            )
        }
    }

}
@Composable
private fun ChatBubble(
    viewModel: ChatBotViewModel,
    content: String
){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 10.dp)
                .padding(start = 40.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ){
            Column(
                modifier = Modifier
                    .padding(end = 10.dp)
                ,
            ) {
                Text(
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.purple_main),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(vertical = 7.dp, horizontal = 5.dp)
                    ,
                    text = content,
                    color = Color.White
                )
            }
        }
        ChatBotBubble(content = content, isInit = false)
    }

}

@Composable
private fun ChatBotBubble(content: String, isInit: Boolean) {
    var answer by remember{ mutableStateOf("") }
    val initText = stringResource(id = R.string.chatBot_base_chat)

    LaunchedEffect(Unit){
        withContext(Dispatchers.IO){
            val query = JSONObject()
            query.put("content", content)

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(ChatBotViewModel.CHAT_URL)
                .post(
                    query.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                )
                .build()

            client.newCall(request).execute().use { response ->
                if(!response.isSuccessful) throw IOException("Unexpected code $response")

                response.body?.source()?.let { source->
                    val buffer = okio.Buffer()
                    while(source.read(buffer, 128) != -1L){
                        val data = buffer.readUtf8()

                        withContext(Dispatchers.Main){
                            answer += data
                        }
                    }
                }
            }
        }

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 10.dp)
            .padding(start = 10.dp)
        ,
        horizontalArrangement = Arrangement.Start
    ){
        Surface(
            modifier = Modifier
                .size(40.dp)
            ,
            shape = CircleShape
        ) {
            Image(
                modifier = Modifier
                    .padding(5.dp)
                ,
                painter = painterResource(id = R.drawable.ic_app_icon),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 5.dp)
                .padding(end = 40.dp)
            ,
        ) {

            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.chatBot_name),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.purple_main)
            )
            if(answer == "" && !isInit){
                LottieChatAnimation(
                    modifier = Modifier
                    ,
                    isCompleted = false
                )
            }else{
                Text(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(vertical = 7.dp, horizontal = 5.dp)
                    ,
                    text = if(!isInit) answer else initText,
                )
            }

        }

    }
}
