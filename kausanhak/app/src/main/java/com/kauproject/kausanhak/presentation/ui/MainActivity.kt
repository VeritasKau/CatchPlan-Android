package com.kauproject.kausanhak.presentation.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.kauproject.kausanhak.data.remote.service.login.SignInService
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity(): ComponentActivity() {
    @Inject
    lateinit var userDataRepository: UserDataRepository
    @Inject
    lateinit var signInService: SignInService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KausanhakTheme {
                CatchPlanApp(
                    userDataRepository = userDataRepository,
                    signInService = signInService,
                )
            }
        }
    }
}



