package com.avv2050soft.weatherscope.presentation.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.avv2050soft.weatherscope.presentation.MainActivity
import com.avv2050soft.weatherscope.presentation.ui.screens.WeatherViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationUtils(
    private val mainActivity: MainActivity,
    private val weatherViewModel: WeatherViewModel,
    ) {
    private val fusedClient = LocationServices.getFusedLocationProviderClient(mainActivity)

    private val launcher = mainActivity.registerForActivityResult(
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

    private fun stopLocation() {
        fusedClient.removeLocationUpdates(locationCallback)
    }

    fun checkPermissions() {
        if (REQUIRED_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(
                    mainActivity,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            startLocation()
        } else {
            launcher.launch(REQUIRED_PERMISSIONS)
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS: Array<String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}