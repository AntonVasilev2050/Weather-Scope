package com.avv2050soft.weatherscope.domain.models.forecast


data class Weather(
    val alerts: Alerts,
    val current: Current,
    val forecast: Forecast,
    val location: Location
)