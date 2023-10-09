package com.avv2050soft.weatherscope.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.R
import com.avv2050soft.weatherscope.domain.models.forecast.Weather
import com.avv2050soft.weatherscope.presentation.ui.theme.LightGreyTransparent
import com.avv2050soft.weatherscope.presentation.utils.CoilImage
import com.avv2050soft.weatherscope.presentation.utils.formattedDate
import kotlin.math.roundToInt

@Composable
fun TomorrowScreen(
    navHostController: NavHostController,
    screenKey: String,
    weatherViewModel: WeatherViewModel
) {
    weatherViewModel.getLocationFromPreferences()
    val location by remember { weatherViewModel.locationStateFlow }
    weatherViewModel.loadWeather(location)
    val weather by remember { weatherViewModel.weatherStateFlow }
    val hourlyForecast = weather?.forecast?.forecastday?.get(1)?.hour
    if (weather != null) {
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 30.dp)
                    .fillMaxSize(),
            ) {
                FindLocationRow(
                    navHostController = navHostController,
                    weatherViewModel = weatherViewModel
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 0.dp, bottom = 55.dp),
                ) {
                    item {
                        weather?.let { weather ->

                            WeatherDateTime(weather)
                            Spacer(modifier = Modifier.height(16.dp))
                            TemperatureDayNightTomorrow(weather)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp),
                            ) {
                                TemperatureTomorrow(weather = weather)
                                Spacer(modifier = Modifier.width(32.dp))
                                WeatherConditionsTomorrow(weather = weather)
                            }
                            Spacer(modifier = Modifier.height(32.dp))

                            hourlyForecast?.let { WeatherHourly(it, screenKey, weatherViewModel) }
                            Spacer(modifier = Modifier.height(32.dp))
                            WeatherTomorrow(weather = weather)
                            Spacer(modifier = Modifier.height(32.dp))
                            SunriseSunsetTomorrow(weather = weather)
                        }
                    }
                }
            }
        }
    } else {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            text = "Data is loading...",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun WeatherDateTime(weather: Weather) {
    Text(
        text = weather.forecast.forecastday[1].dateEpoch.formattedDate("EEEE, dd MMM", ""),
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
private fun TemperatureDayNightTomorrow(weather: Weather) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.icon_day),
            contentDescription = null
        )
        Text(text = "Day:", modifier = Modifier.padding(end = 4.dp))
        Text(text = weather.forecast.forecastday[1].day.maxtempC.roundToInt().toString())
        Text(text = "°", modifier = Modifier.padding(end = 24.dp))
        Icon(
            painter = painterResource(id = R.drawable.icon_night),
            contentDescription = null
        )
        Text(text = "Night:", modifier = Modifier.padding(end = 4.dp))
        Text(text = weather.forecast.forecastday[1].day.mintempC.roundToInt().toString())
        Text(text = "°", modifier = Modifier.padding(end = 8.dp))
    }
}

@Composable
private fun TemperatureTomorrow(weather: Weather) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Row {
            Text(
                text = weather.forecast.forecastday[1].day.avgtempC.roundToInt().toString(),
                fontSize = 96.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                "°C",
                modifier = Modifier.padding(top = 20.dp),
                fontSize = 48.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun WeatherConditionsTomorrow(weather: Weather) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            data = "https:${weather.forecast.forecastday[1].day.condition.icon}",
            Modifier.size(120.dp),
            contentDescription = "Picture of the weather conditions",
            alignment = Alignment.BottomEnd
        )
        Text(
            text = weather.forecast.forecastday[1].day.condition.text,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
private fun WeatherTomorrow(weather: Weather) {
    Column {
        Text(text = "Tomorrow:")
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Wind Max",
                modifier = Modifier.fillMaxWidth(Fraction04),
                color = LightGreyTransparent
            )
            Text(text = "${weather.forecast.forecastday[1].day.maxwindKph.roundToInt()} kph")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Humidity",
                modifier = Modifier.fillMaxWidth(Fraction04),
                color = LightGreyTransparent
            )
            Text(text = "${weather.forecast.forecastday[1].day.avghumidity.roundToInt()}%")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "UV index",
                modifier = Modifier.fillMaxWidth(Fraction04),
                color = LightGreyTransparent
            )
            Text(text = "${weather.forecast.forecastday[1].day.uv.roundToInt()}")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Visibility",
                modifier = Modifier.fillMaxWidth(Fraction04),
                color = LightGreyTransparent
            )
            Text(text = "${weather.forecast.forecastday[1].day.avgvisKm.roundToInt()} km")
        }
    }
}

@Composable
private fun SunriseSunsetTomorrow(weather: Weather) {
    Column {
        Text(text = "Sunrise and Sunset tomorrow")
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Sunrise", color = LightGreyTransparent)
                Text(
                    text = weather.forecast.forecastday[1].astro.sunrise,
                    fontSize = 28.sp, fontWeight = FontWeight.Thin
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Sunset", color = LightGreyTransparent)
                Text(
                    text = weather.forecast.forecastday[1].astro.sunset,
                    fontSize = 28.sp, fontWeight = FontWeight.Thin
                )
            }
        }
    }
}

private const val Fraction04 = 0.4f