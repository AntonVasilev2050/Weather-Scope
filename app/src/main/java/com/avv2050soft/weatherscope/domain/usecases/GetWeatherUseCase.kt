package com.avv2050soft.weatherscope.domain.usecases

import com.avv2050soft.weatherscope.domain.models.forecast.Weather
import com.avv2050soft.weatherscope.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend fun getWeather(
        location: String,
        days: Int,
        aqi: String,
        alerts: String,
        lang: String
    ): Weather {
        return repository.getWeather(
            location = location,
            days = days,
            aqi = aqi,
            alerts = alerts,
            lang = lang
        )
    }
}