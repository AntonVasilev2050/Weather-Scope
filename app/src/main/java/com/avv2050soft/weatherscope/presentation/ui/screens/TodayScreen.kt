package com.avv2050soft.weatherscope.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.R
import com.avv2050soft.weatherscope.domain.models.forecast.Hour
import com.avv2050soft.weatherscope.domain.models.forecast.Weather
import com.avv2050soft.weatherscope.presentation.utils.CoilImage
import com.avv2050soft.weatherscope.presentation.utils.formattedDate
import kotlin.math.roundToInt

@Composable
fun TodayScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    val weatherViewModel = hiltViewModel<WeatherViewModel>()
    weatherViewModel.getLocationFromPreferences()
    val location by remember { weatherViewModel.locationStateFlow }
    weatherViewModel.loadWeather(location)
    val weather by remember { weatherViewModel.weatherStateFlow }
    val hourlyForecast = weather?.forecast?.forecastday?.get(0)?.hour
    Surface(
        color = colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 90.dp)
                .fillMaxSize(),
        ) {
            if (weather != null) {
                weather?.let { weather ->
                    FindLocationRow(navHostController = navHostController)
                    Spacer(modifier = Modifier.height(16.dp))
                    WeatherDateTime(weather)
                    Spacer(modifier = Modifier.height(16.dp))
                    TemperatureDayNight(weather)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp),
                    ) {
                        Temperature(weather = weather)
                        Spacer(modifier = Modifier.width(32.dp))
                        WeatherConditions(weather = weather)
                    }
                }
                Spacer(modifier = Modifier.height(64.dp))
                Text(text = "Weather hourly:")
                Spacer(modifier = Modifier.height(4.dp))
                hourlyForecast?.let { WeatherHourly(it) }
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
    }
}

@Composable
private fun WeatherDateTime(weather: Weather) {
    Text(
        text = weather.current.lastUpdatedEpoch.formattedDate(
            "dd MMMM, HH:mm",
            weather.location.tzId
        ),
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
private fun TemperatureDayNight(weather: Weather) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.baseline_light_mode_24),
            contentDescription = null
        )
        Text(text = "Day:", modifier = Modifier.padding(end = 4.dp))
        Text(text = weather.forecast.forecastday[0].day.maxtempC.roundToInt().toString())
        Text(text = "°", modifier = Modifier.padding(end = 24.dp))
        Icon(
            painter = painterResource(id = R.drawable.baseline_mode_night_24),
            contentDescription = null
        )
        Text(text = "Night:", modifier = Modifier.padding(end = 4.dp))
        Text(text = weather.forecast.forecastday[0].day.mintempC.roundToInt().toString())
        Text(text = "°", modifier = Modifier.padding(end = 8.dp))
    }
}

@Composable
private fun Temperature(weather: Weather) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Row {
            Text(
                text = weather.current.tempC.roundToInt().toString(),
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
        Row {
            Text(text = "Fills like ", modifier = Modifier.padding(end = 4.dp))
            Text(text = weather.current.feelslikeC.roundToInt().toString())
            Text(text = "°")
        }
    }
}

@Composable
fun WeatherConditions(weather: Weather) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            data = "https:${weather.current.condition.icon}",
            Modifier.size(120.dp),
            contentDescription = "Picture of the weather conditions",
            alignment = Alignment.BottomEnd
        )
        Text(
            text = weather.current.condition.text,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun WeatherHourly(hourlyForecast: List<Hour>) {
    val tempFontSize = 16.sp
    LazyRow {
        items(items = hourlyForecast) { hourForecast ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(hourForecast.tempC.roundToInt().toString(), fontSize = tempFontSize)
                    Text(text = "°", fontSize = tempFontSize)
                }
                CoilImage(
                    data = "https:${hourForecast.condition.icon}",
                    Modifier.size(50.dp),
                    contentDescription = "Picture of the weather conditions",
                    alignment = Alignment.BottomCenter
                )
                Divider(modifier = Modifier.width(50.dp), color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = hourForecast.time.takeLast(5),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}



