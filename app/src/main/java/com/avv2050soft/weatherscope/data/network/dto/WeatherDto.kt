package com.avv2050soft.weatherscope.data.network.dto

import com.avv2050soft.weatherscope.domain.models.forecast.Alerts
import com.avv2050soft.weatherscope.domain.models.forecast.Current
import com.avv2050soft.weatherscope.domain.models.forecast.Forecast
import com.avv2050soft.weatherscope.domain.models.forecast.Location
import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("alerts")
    val alerts: Alerts,
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)
