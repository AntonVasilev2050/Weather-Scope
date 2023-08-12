package com.avv2050soft.weatherscope.presentation.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avv2050soft.weatherscope.domain.models.forecast.Weather
import com.avv2050soft.weatherscope.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private var weather: Weather? = null
    private val _weatherStateFlow = MutableStateFlow(weather)
    val weatherStateFlow = _weatherStateFlow.asStateFlow()

    private val _message = Channel<String>()
    val message = _message.receiveAsFlow()

    fun loadWeather(location: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                weather = repository.getWeather(
                    location = location,
                    days = 3,
                    aqi = "yes",
                    alerts = "yes",
                    lang = "ru"
                )
            }
                .onSuccess { _weatherStateFlow.value = weather }
                .onFailure {
                    _message.send("An error occurred when getting weather")
                    Log.d("data_test", it.message.toString())
                }
        }
    }
}