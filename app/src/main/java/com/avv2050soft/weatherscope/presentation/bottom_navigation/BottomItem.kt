package com.avv2050soft.weatherscope.presentation.bottom_navigation

import com.avv2050soft.weatherscope.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    object Today : BottomItem("Today", R.drawable.today_icon, "today_screen")

    object Tomorrow : BottomItem("Tomorrow", R.drawable.tomorrow_icon, "tomorrow_screen")

    object Forecast : BottomItem("Forecast", R.drawable.calendar_icon, "forecast_screen")
}