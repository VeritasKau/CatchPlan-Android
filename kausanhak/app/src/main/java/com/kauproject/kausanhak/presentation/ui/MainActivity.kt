package com.kauproject.kausanhak.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.ui.calendar.CalendarScreen
import com.kauproject.kausanhak.presentation.ui.chatbot.ChatBotScreen
import com.kauproject.kausanhak.presentation.ui.event.EventScreen
import com.kauproject.kausanhak.presentation.ui.favorite.FavoriteScreen
import com.kauproject.kausanhak.presentation.ui.login.LoginViewModel
import com.kauproject.kausanhak.presentation.ui.mypage.MyPageScreen
import com.kauproject.kausanhak.presentation.ui.theme.CALENDAR
import com.kauproject.kausanhak.presentation.ui.theme.CHATBOT
import com.kauproject.kausanhak.presentation.ui.theme.EVENT
import com.kauproject.kausanhak.presentation.ui.theme.FAVORITE
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import com.kauproject.kausanhak.presentation.ui.theme.MYPAGE
import javax.inject.Inject

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KausanhakTheme {
                CatchPlanApp()
            }
        }
    }
}



