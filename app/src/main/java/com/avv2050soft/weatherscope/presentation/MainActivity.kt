package com.avv2050soft.weatherscope.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.avv2050soft.weatherscope.presentation.ui.screens.MainScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.WeatherViewModel
import com.avv2050soft.weatherscope.presentation.ui.theme.WeatherScopeTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedClient: FusedLocationProviderClient
    private val weatherViewModel: WeatherViewModel by viewModels()

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        if (map.values.isNotEmpty() && map.values.all { it }) {
            startLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocation() {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            500
        ).build()

        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result.locations.let {
                val currentLatitude = it[it.lastIndex].latitude
                val currentLongitude = it[it.lastIndex].longitude

                weatherViewModel.saveLocationToPreferences("$currentLatitude,$currentLongitude")
                stopLocation()
            }
        }
    }

    private fun stopLocation(){
        fusedClient.removeLocationUpdates(locationCallback)
    }

    fun checkPermissions() {
        if (REQUIRED_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            startLocation()
        } else {
            launcher.launch(REQUIRED_PERMISSIONS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            WeatherScopeTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        navController = navController,
                        weatherViewModel
                    )
                }
            }
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS: Array<String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}
