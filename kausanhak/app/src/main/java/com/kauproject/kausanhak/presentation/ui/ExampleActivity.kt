package com.kauproject.kausanhak.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.kauproject.kausanhak.ui.theme.KausanhakTheme

class ExampleActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultExView(){
    KausanhakTheme {
        eventCard()
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExView(){
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            onClick = { expanded = !expanded }
        ) {
            AnimatedContent(
                modifier = Modifier
                    .background(Color.Blue)
                ,
                targetState = expanded,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150, 150)) togetherWith
                            fadeOut(animationSpec = tween(150)) using
                            SizeTransform{initialSize, targetSize ->
                                if(targetState){
                                    keyframes {
                                        IntSize(targetSize.width, initialSize.height) at 150
                                        durationMillis = 100
                                    }
                                }else{
                                    keyframes {
                                        IntSize(initialSize.width, targetSize.height) at 150
                                        durationMillis = 100
                                    }
                                }
                            }
                }
            ) {targetState ->
                if(targetState){
                    ex1()
                }else{
                    ex2()
                }
            }

        }
        
    }

}

@Composable
fun ex1(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Text(
            text = "안녕하세요 안녕하세요 안녕하세요 안녕하세요 " +
                    "안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Button(
            onClick = { /*TODO*/ }) {

        }
    }
}

@Composable
fun ex2(){
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .background(Color.Blue)
        ,
    ){
        Text(
            text = "예제입니다",
            color = Color.White
        )
    }
}

@Composable
fun eventCard(){
    val imageUrl = "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif"
    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
    ){
        Column(
            modifier = Modifier
        ) {


        }

    }
}