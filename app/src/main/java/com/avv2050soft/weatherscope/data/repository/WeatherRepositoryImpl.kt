package com.avv2050soft.weatherscope.data.repository

import com.avv2050soft.weatherscope.data.api.WeatherApi
import com.avv2050soft.weatherscope.domain.models.autocomplete.Autocomplete
import com.avv2050soft.weatherscope.domain.models.forecast.Weather
import com.avv2050soft.weatherscope.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor() : WeatherRepository {
    override suspend fun getWeather(
        location: String,
        days: Int,
        aqi: String,
        alerts: String,
        lang: String
    ): Weather {
        return WeatherApi.create().getWeather(
            location = location,
            days = days,
            aqi = aqi,
            alerts = alerts,
            language = lang
        )
    }

    override suspend fun search(location: String): Autocomplete {
        return WeatherApi.create().search(location = location)
    }
}

const val LocationNameKey = "location name key"