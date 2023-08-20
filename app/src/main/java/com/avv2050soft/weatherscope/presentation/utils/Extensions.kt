package com.avv2050soft.weatherscope.presentation.utils

import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

fun Int.formattedDate(format:String, timeZone: String): String  {
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone(timeZone)
    val date = Date(this * 1000L)
    return  simpleDateFormat.format(date)
}