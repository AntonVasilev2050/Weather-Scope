package com.avv2050soft.weatherscope.data.mappers

import com.avv2050soft.weatherscope.data.network.dto.WeatherDto
import com.avv2050soft.weatherscope.domain.models.forecast.Weather

class WeatherMapper {
    fun mapWeatherDtoToWeather(weatherDto: WeatherDto) = Weather(
        alerts = weatherDto.alerts,
        current = weatherDto.current,
        forecast = weatherDto.forecast,
        location = weatherDto.location
    )
}