package com.avv2050soft.weatherscope.domain.models.forecast


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastday: List<Forecastday>
)