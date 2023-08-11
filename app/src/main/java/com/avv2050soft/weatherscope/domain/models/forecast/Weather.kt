package com.avv2050soft.weatherscope.domain.models.forecast


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("alerts")
    val alerts: Alerts,
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)