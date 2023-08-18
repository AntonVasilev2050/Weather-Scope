package com.avv2050soft.weatherscope.presentation.utils

import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

fun Int.formattedDate(format:String): String  {
    val date = Date(this * 1000L )
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return  simpleDateFormat.format(date)
}