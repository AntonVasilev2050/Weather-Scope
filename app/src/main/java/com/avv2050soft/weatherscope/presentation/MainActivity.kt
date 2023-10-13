package com.avv2050soft.weatherscope.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.avv2050soft.weatherscope.presentation.ui.screens.MainScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.WeatherViewModel
import com.avv2050soft.weatherscope.presentation.ui.theme.WeatherScopeTheme
import com.avv2050soft.weatherscope.presentation.utils.LocationUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    lateinit var locationUtils: LocationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationUtils = LocationUtils(this, weatherViewModel)

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
}
