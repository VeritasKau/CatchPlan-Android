package com.kauproject.kausanhak.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.kauproject.kausanhak.data.remote.service.info.GetUserInfoService
import com.kauproject.kausanhak.data.remote.service.login.CheckMemberService
import com.kauproject.kausanhak.data.remote.service.login.SignInService
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import com.kauproject.kausanhak.presentation.ui.theme.KausanhakTheme
import com.kauproject.kausanhak.presentation.util.RequestPermissionUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity(): ComponentActivity() {
    @Inject
    lateinit var userDataRepository: UserDataRepository
    @Inject
    lateinit var signInService: SignInService
    @Inject
    lateinit var checkMemberService: CheckMemberService
    @Inject
    lateinit var getUserInfoService: GetUserInfoService

    override fun onStart() {
        super.onStart()
        RequestPermissionUtil(this).requestLocation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this@MainActivity

        getLocation()

        setContent {
            KausanhakTheme {
                CatchPlanApp(
                    userDataRepository = userDataRepository,
                    signInService = signInService,
                    checkMemberService = checkMemberService,
                    getUserInfoService = getUserInfoService,
                    context = context
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        val fusedLocationProviderClient
        = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { success: Location? ->
                success?.let { location ->
                    val address = getAddress(location.latitude, location.longitude)?.get(0)
                    lifecycleScope.launch {
                        address?.let {
                            userDataRepository.setUserData("location", "경기도")
                            Log.d("TEST LOCATION2", it.adminArea)
                        }
                    }
                }
            }
            .addOnFailureListener { fail->
                Log.d("TEST", "FAIL:${fail.message}")
            }
    }

    private fun getAddress(lat: Double, lng: Double): List<Address>?{
        lateinit var address: List<Address>

        return try{
            val geocoder = Geocoder(this, Locale.KOREA)
            address = geocoder.getFromLocation(lat, lng, 1) as List<Address>
            address
        } catch (e: IOException){
            null
        }
    }
}



