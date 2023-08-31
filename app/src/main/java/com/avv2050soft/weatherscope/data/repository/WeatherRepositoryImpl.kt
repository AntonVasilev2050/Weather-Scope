package com.avv2050soft.weatherscope.data.repository

import com.avv2050soft.weatherscope.data.mappers.WeatherMapper
import com.avv2050soft.weatherscope.data.network.api.WeatherApi
import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem
import com.avv2050soft.weatherscope.domain.models.forecast.Weather
import com.avv2050soft.weatherscope.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val mapper: WeatherMapper
) : WeatherRepository {
    override suspend fun getWeather(
        location: String,
        days: Int,
        aqi: String,
        alerts: String,
        lang: String
    ): Weather {
        return mapper.mapWeatherDtoToWeather(
            WeatherApi.create().getWeather(
                location = location,
                days = days,
                aqi = aqi,
                alerts = alerts,
                language = lang
            )
        )
    }

    override suspend fun search(location: String): List<AutocompleteItem> {
        return WeatherApi.create().search(location = location)
    }
}
