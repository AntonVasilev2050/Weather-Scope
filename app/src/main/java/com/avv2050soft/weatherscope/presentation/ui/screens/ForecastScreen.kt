package com.avv2050soft.weatherscope.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.domain.models.forecast.Forecastday
import com.avv2050soft.weatherscope.domain.models.forecast.Hour
import com.avv2050soft.weatherscope.presentation.navigation.SavedLocations
import com.avv2050soft.weatherscope.presentation.ui.theme.LightGreyTransparent
import com.avv2050soft.weatherscope.presentation.utils.CoilImage
import com.avv2050soft.weatherscope.presentation.utils.formattedDate
import com.avv2050soft.weatherscope.presentation.utils.navigateSingleTopTo
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
    val forecastDayList: List<Forecastday> = weather?.forecast?.forecastday ?: emptyList()
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .wrapContentHeight()
                .clickable { navHostController.navigateSingleTopTo(SavedLocations.route) },
            text = "Weather forecast for \n $location",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp, bottom = 70.dp)
        ) {
            items(items = forecastDayList) { forecastDay ->
                var isExpanded by rememberSaveable { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .clickable { isExpanded = !isExpanded }
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.LightGray,
                        thickness = 3.dp
                    )
                    BasicForecast(forecastDay)
                    DetailsForecast(forecastDay, isExpanded)
                    WeatherDayHourly(forecastDayHour = forecastDay.hour, isExpanded)
                    Spacer(modifier = Height)
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
                .fillMaxWidth(0.7f)
        ) {
            Column {
                Text(
                    text = forecastDay.dateEpoch.formattedDate("EEEE, dd MMM"),
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = forecastDay.day.condition.text)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CoilImage(
                data = "https:${forecastDay.day.condition.icon}",
                Modifier.size(50.dp),
                contentDescription = "Picture of the weather conditions",
                alignment = Alignment.Center
            )
            Column {
                Text(text = "${forecastDay.day.maxtempC.roundToInt()}°")
                Text(text = "${forecastDay.day.mintempC.roundToInt()}°", color = Color.Gray)
            }
        }
    }
}

@Composable
fun DetailsForecast(forecastDay: Forecastday, isExpanded: Boolean) {
    if (isExpanded) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Height)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Wind",
                    modifier = Modifier.fillMaxWidth(Fraction04),
                    color = Color.Gray
                )
                Text(text = "${forecastDay.day.maxwindKph} km/hour")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Humidity",
                    modifier = Modifier.fillMaxWidth(Fraction04),
                    color = Color.Gray
                )
                Text(text = "${forecastDay.day.avghumidity} %")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "UV index",
                    modifier = Modifier.fillMaxWidth(Fraction04),
                    color = Color.Gray
                )
                Text(text = "${forecastDay.day.uv}")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Sunrise/Sunset",
                    modifier = Modifier.fillMaxWidth(Fraction04),
                    color = Color.Gray
                )
                Text(text = "${forecastDay.astro.sunrise} / ${forecastDay.astro.sunset}")
            }
        }
    }
}

@Composable
fun WeatherDayHourly(forecastDayHour: List<Hour>, isExpanded: Boolean) {
    val tempFontSize = 16.sp
    if (isExpanded) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Weather hourly:", color = Color.Gray)
            LazyRow (
                modifier = Modifier.background(LightGreyTransparent)
            ){
                items(items = forecastDayHour) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            Text(it.tempC.roundToInt().toString(), fontSize = tempFontSize)
                            Text(text = "°", fontSize = tempFontSize)
                        }
                        CoilImage(
                            data = "https:${it.condition.icon}",
                            Modifier.size(50.dp),
                            contentDescription = "Picture of the weather conditions",
                            alignment = Alignment.BottomCenter
                        )
                        Text(text = it.time.takeLast(5), color = Color.Black, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

private val Height = Modifier.height(16.dp)
private const val Fraction04 = 0.4f
