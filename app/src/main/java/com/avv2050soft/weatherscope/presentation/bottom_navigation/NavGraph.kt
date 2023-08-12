package com.avv2050soft.weatherscope.presentation.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.avv2050soft.weatherscope.presentation.ui.screens.ForecastScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.TodayScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.TomorrowScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.WeatherViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController,
    weatherViewModel: WeatherViewModel
//    banners: List<TopBanner>,
//    categories: List<Category>,
//    meals: List<Meal>,
//    onClickCategory: (String) -> Unit
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
    }
}