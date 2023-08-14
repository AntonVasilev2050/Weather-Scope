package com.avv2050soft.weatherscope.domain.repository

import com.avv2050soft.weatherscope.domain.models.autocomplete.Autocomplete
import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem
import com.avv2050soft.weatherscope.domain.models.forecast.Weather

interface WeatherRepository {
    suspend fun getWeather(location: String, days: Int, aqi: String, alerts: String, lang: String): Weather
    suspend fun search(location: String): List<AutocompleteItem>
}