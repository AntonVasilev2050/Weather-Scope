package com.avv2050soft.weatherscope.presentation.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.avv2050soft.weatherscope.presentation.ui.screens.ForecastScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.LocationsScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.TodayScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.TomorrowScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.WeatherViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController,
    weatherViewModel: WeatherViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = "today_screen",
    ){
        composable("today_screen"){
            TodayScreen(
                modifier = Modifier,
                weatherViewModel,
                location = "Krasnodar"
            )
        }
        composable("tomorrow_screen"){
            TomorrowScreen()
        }
        composable("forecast_screen"){
            ForecastScreen()
        }
        composable("locations_screen"){
            LocationsScreen()
        }
    }
}

