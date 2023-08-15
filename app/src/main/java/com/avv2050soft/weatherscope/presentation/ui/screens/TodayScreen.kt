package com.avv2050soft.weatherscope.presentation.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.R
import com.avv2050soft.weatherscope.domain.models.forecast.Hour
import com.avv2050soft.weatherscope.domain.models.forecast.Weather
import com.avv2050soft.weatherscope.presentation.utils.CoilImage
import kotlin.math.roundToInt

@Composable
fun TodayScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    navHostController: NavHostController,
    location: String
) {
    weatherViewModel.loadWeather(location)
    val weather by remember { weatherViewModel.weatherStateFlow }.collectAsState()
//    val weather = weatherViewModel.weatherStateFlow.collectAsState().value
    val hourlyForecast = weather?.forecast?.forecastday?.get(0)?.hour
    Surface(
        color = colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 90.dp)
                .fillMaxSize(),
        ) {
            weather?.let {
                FindLocationRow(
                    weather = it,
                    navHostController = navHostController,
                    weatherViewModel = weatherViewModel
                )
                Spacer(modifier = Modifier.height(16.dp))
                WeatherDateTime(it)
                Spacer(modifier = Modifier.height(16.dp))
                TemperatureDayNight(it)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                ) {
                    Temperature(weather = it)
                    Spacer(modifier = Modifier.width(32.dp))
                    WeatherConditions(weather = it)
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
            hourlyForecast?.let { WeatherHourly(it) }
        }
    }
}

@Composable
private fun WeatherDateTime(weather: Weather) {
    Text(
        text = weather.current.lastUpdated,
        color = Color.Black
    )
}

@Composable
private fun TemperatureDayNight(weather: Weather) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.baseline_light_mode_24),
            contentDescription = null
        )
        Text(text = "Day:", modifier = Modifier.padding(end = 4.dp), )
        Text(text = weather.forecast.forecastday[0].day.maxtempC.roundToInt().toString())
        Text( text = "°", modifier = Modifier.padding(end = 24.dp),)
        Icon(
            painter = painterResource(id = R.drawable.baseline_mode_night_24),
            contentDescription = null
        )
        Text( text = "Night:", modifier = Modifier.padding(end = 4.dp),)
        Text(text = weather.forecast.forecastday[0].day.mintempC.roundToInt().toString())
        Text( text = "°", modifier = Modifier.padding(end = 8.dp),)
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
        horizontalAlignment = Alignment.Start
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
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(

                ) {
                    Text(hourForecast.tempC.roundToInt().toString(), fontSize = tempFontSize)
                    Text(text = "°", fontSize = tempFontSize)
                }
                Spacer(modifier = Modifier.height(16.dp))
                CoilImage(
                    data = "https:${hourForecast.condition.icon}",
                    Modifier.size(50.dp),
                    contentDescription = "Picture of the weather conditions",
                    alignment = Alignment.BottomCenter
                )
                Divider(modifier = Modifier.width(70.dp), color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = hourForecast.time.takeLast(5), color = Color.Black, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun HorizontalLine() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawLine(
            color = Color.Black,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = 2.dp.toPx()
        )
    }
}


