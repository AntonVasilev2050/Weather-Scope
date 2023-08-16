package com.avv2050soft.weatherscope.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.avv2050soft.weatherscope.presentation.ui.screens.AutocompleteLocationScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.ForecastScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.SavedLocationsScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.TodayScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.TomorrowScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.WeatherViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherScopeNavHost(
    navHostController: NavHostController,
    weatherViewModel: WeatherViewModel,
) {
    weatherViewModel.getLocationFromPreferences()
    val location by remember {weatherViewModel.locationStateFlow}.collectAsState()

    NavHost(
        navController = navHostController,
        startDestination = "today_screen",
    ) {
        composable("today_screen") {
            TodayScreen(
                modifier = Modifier,
                weatherViewModel = weatherViewModel,
                navHostController = navHostController,
                location = location
            )
        }
        composable("tomorrow_screen") {
            TomorrowScreen()
        }
        composable("forecast_screen") {
            ForecastScreen(
                modifier = Modifier,
                weatherViewModel = weatherViewModel,
                navHostController = navHostController,
                location = location
            )
        }
        composable("saved_locations_screen") {
            SavedLocationsScreen(
                modifier = Modifier,
                weatherViewModel = weatherViewModel,
                navHostController = navHostController,
            )
        }
        composable("autocomplete_location_screen"){
            AutocompleteLocationScreen(
                modifier = Modifier,
                weatherViewModel = weatherViewModel,
                navHostController = navHostController,
            )
        }
    }
}


