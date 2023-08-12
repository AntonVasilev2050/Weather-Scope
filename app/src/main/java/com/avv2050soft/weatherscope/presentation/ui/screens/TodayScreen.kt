package com.avv2050soft.weatherscope.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avv2050soft.weatherscope.R
import com.avv2050soft.weatherscope.domain.models.forecast.Hour

@Composable
fun TodayScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    location: String
) {
    weatherViewModel.loadWeather(location)
    val weather by remember{weatherViewModel.weatherStateFlow}.collectAsState()
//    val weather = weatherViewModel.weatherStateFlow.collectAsState().value
    val hourlyForecast = weather?.forecast?.forecastday?.get(0)?.hour
    Surface(
        color = colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 60.dp, bottom = 90.dp)
                .fillMaxSize(),
        ) {
            WeatherDateTime()
            Spacer(modifier = Modifier.height(16.dp))
            TemperatureDayNight()
            TemperatureAndIcon()
            WeatherConditions()
            Spacer(modifier = Modifier.height(16.dp))
            if (hourlyForecast != null) {
                WeatherHourly(hourlyForecast)
            }
        }
    }
}

@Composable
private fun WeatherDateTime() {
    Text(
        text = "11 августа, 06:57",
        color = Color.Black
    )
}

@Composable
private fun TemperatureDayNight() {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.baseline_light_mode_24),
            contentDescription = null
        )
        Text(modifier = Modifier.padding(end = 4.dp), text = "Day:")
        Text(text = "27")
        Text(modifier = Modifier.padding(end = 8.dp), text = "°")
        Icon(
            painter = painterResource(id = R.drawable.baseline_mode_night_24),
            contentDescription = null
        )
        Text(modifier = Modifier.padding(end = 4.dp), text = "Night:")
        Text(text = "18")
        Text(modifier = Modifier.padding(end = 8.dp), text = "°")
    }
}

@Composable
private fun TemperatureAndIcon() {
    Row {
        Text(
            "27",
            fontSize = 96.sp,
            fontFamily = FontFamily.Monospace,
        )
        Text(
            "°C",
            modifier = Modifier.padding(top = 20.dp),
            fontSize = 48.sp
        )
        Spacer(modifier = Modifier.width(24.dp))
        Image(
            painter = painterResource(id = R.drawable.havy_rain_thunder),
            contentDescription = null,
            alignment = Alignment.CenterEnd,
            modifier = Modifier
                .requiredSize(100.dp, 130.dp)
                .padding(top = 20.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun WeatherConditions() {
    Row {
        Text(text = "Ощущается как", modifier = Modifier.padding(end = 4.dp))
        Text(text = "29")
        Text(text = "°")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Дождь с грозой")
        }
    }
}

@Composable
fun WeatherHourly(hourlyList: List<Hour>) {
    LazyRow{
        items(items = hourlyList){
            Column(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
               Text(it.tempC.toString())
            }
        }
    }
}


