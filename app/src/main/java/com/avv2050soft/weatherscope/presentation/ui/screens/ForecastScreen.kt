package com.avv2050soft.weatherscope.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.domain.models.forecast.Forecastday
import com.avv2050soft.weatherscope.presentation.utils.CoilImage
import kotlin.math.roundToInt

@Composable
fun ForecastScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    navHostController: NavHostController,
    location: String
) {
    weatherViewModel.loadWeather(location)
    val weather by remember { weatherViewModel.weatherStateFlow }.collectAsState()
    val hourlyForecast = weather?.forecast?.forecastday?.get(0)?.hour
    val forecastDay: List<Forecastday> = weather?.forecast?.forecastday ?: emptyList()
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .wrapContentHeight(),
            text = "Forecast Screen",
            textAlign = TextAlign.Center,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp, bottom = 60.dp)
        ) {
            items(items = forecastDay) { forecastDay ->
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    BasicForecast(forecastDay)
//                    Spacer(modifier = Modifier.height(16.dp))
                    DetailsForecast(forecastDay)
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
fun BasicForecast(forecastDay: Forecastday) {
    Row(
        modifier = Modifier
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
        ) {
            Column {
                Text(text = forecastDay.date)
                Text(text = forecastDay.day.condition.text)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CoilImage(
                data = "https:${forecastDay.day.condition.icon}",
                Modifier.size(80.dp),
                contentDescription = "Picture of the weather conditions",
                alignment = Alignment.Center
            )
            Column {
                Text(text = "${forecastDay.day.maxtempC.roundToInt()}°")
                Text(text = "${forecastDay.day.mintempC.roundToInt()}°")
            }
        }
    }
}

@Composable
fun DetailsForecast(forecastDay: Forecastday) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "Wind", modifier = Modifier.fillMaxWidth(0.4f))
            Text(text = "${forecastDay.day.maxwindKph} km/hour")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "Humidity", modifier = Modifier.fillMaxWidth(0.4f))
            Text(text = "${forecastDay.day.avghumidity} %")
        }
    }
}


