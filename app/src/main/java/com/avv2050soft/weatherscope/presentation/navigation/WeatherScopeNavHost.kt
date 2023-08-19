package com.avv2050soft.weatherscope.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
) {
    val weatherViewModel = hiltViewModel<WeatherViewModel>()
    weatherViewModel.getLocationFromPreferences()

    NavHost(
        navController = navHostController,
        startDestination = "today_screen",
    ) {
        composable("today_screen") {
            TodayScreen(
                modifier = Modifier,
                navHostController = navHostController
            )
        }
        composable("tomorrow_screen") {
            TomorrowScreen()
        }
        composable("forecast_screen") {
            ForecastScreen(
                modifier = Modifier,
                navHostController = navHostController,
            )
        }
        composable("saved_locations_screen") {
            SavedLocationsScreen(
                modifier = Modifier,
                navHostController = navHostController,
            )
        }
        composable("autocomplete_location_screen"){
            AutocompleteLocationScreen(
                modifier = Modifier,
                navHostController = navHostController,
            )
        }
    }
}


