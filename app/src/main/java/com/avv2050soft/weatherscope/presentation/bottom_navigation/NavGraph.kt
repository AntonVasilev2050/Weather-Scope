package com.avv2050soft.weatherscope.presentation.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.avv2050soft.weatherscope.presentation.ui.screens.ForecastScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.TodayScreen
import com.avv2050soft.weatherscope.presentation.ui.screens.TomorrowScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
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
            TodayScreen()
        }
        composable("tomorrow_screen"){
            TomorrowScreen()
        }
        composable("forecast_screen"){
            ForecastScreen()
        }
    }
}