package com.avv2050soft.weatherscope.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    weatherViewModel: WeatherViewModel
) {
    weatherViewModel.getLocationFromPreferences()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val screenKey = navBackStackEntry?.destination?.route ?: ""
    NavHost(
        navController = navHostController,
        startDestination = "today_screen",
    ) {
        composable("today_screen") {
            TodayScreen(
                navHostController = navHostController,
                screenKey = screenKey,
                weatherViewModel = weatherViewModel
            )
        }
        composable("tomorrow_screen") {
            TomorrowScreen(
                navHostController = navHostController,
                screenKey = screenKey,
                weatherViewModel = weatherViewModel
            )
        }
        composable("forecast_screen") {
            ForecastScreen(
                navHostController = navHostController,
                screenKey = screenKey,
                weatherViewModel = weatherViewModel
            )
        }
        composable("saved_locations_screen") {
            SavedLocationsScreen(
                navHostController = navHostController,
                screenKey = screenKey,
                weatherViewModel = weatherViewModel
            )
        }
        composable("autocomplete_location_screen") {
            AutocompleteLocationScreen(
                navHostController = navHostController,
                screenKey = screenKey,
                weatherViewModel = weatherViewModel
            )
        }
    }
}


