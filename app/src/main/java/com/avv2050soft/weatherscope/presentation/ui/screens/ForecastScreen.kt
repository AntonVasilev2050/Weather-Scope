package com.avv2050soft.weatherscope.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.domain.models.forecast.Forecastday
import com.avv2050soft.weatherscope.domain.models.forecast.Hour
import com.avv2050soft.weatherscope.presentation.ui.theme.LightGreyTransparent
import com.avv2050soft.weatherscope.presentation.utils.LoadImage
import com.avv2050soft.weatherscope.presentation.utils.formattedDate
import kotlin.math.roundToInt

@Composable
fun ForecastScreen(
    navHostController: NavHostController,
    screenKey: String,
    weatherViewModel: WeatherViewModel
) {
    weatherViewModel.getLocationFromPreferences()
    val location by remember { weatherViewModel.locationStateFlow }
    weatherViewModel.loadWeather(location)
    val weather by remember { weatherViewModel.weatherStateFlow }
    val forecastDayList: List<Forecastday> = weather?.forecast?.forecastday ?: emptyList()
    val lazyListState = weatherViewModel.scrollStates.getOrPut(screenKey) { LazyListState() }

    DisposableEffect(lazyListState) {
        onDispose {
            weatherViewModel.scrollStates[screenKey] = lazyListState
        }
    }
    Surface(
        color = colorScheme.primary
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
                    .padding(top = 8.dp, bottom = 55.dp),
                state = lazyListState
            ) {
                items(items = forecastDayList) { forecastDay ->
                    var isExpanded by rememberSaveable { mutableStateOf(false) }
                    Column(
                        modifier = Modifier
                            .clickable { isExpanded = !isExpanded }
                            .padding(start = 0.dp, end = 0.dp)
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
                    text = forecastDay.dateEpoch.formattedDate("EEEE, dd MMM", ""),
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = forecastDay.day.condition.text)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LoadImage(
                data = "https:${forecastDay.day.condition.icon}",
                Modifier.size(50.dp),
                contentDescription = "Picture of the weather conditions",
                alignment = Alignment.Center
            )
            Column {
                Text(text = "${forecastDay.day.maxtempC.roundToInt()}°")
                Text(
                    text = "${forecastDay.day.mintempC.roundToInt()}°",
                    color = LightGreyTransparent
                )
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
                    color = LightGreyTransparent
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
                    color = LightGreyTransparent
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
                    color = LightGreyTransparent
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
                    color = LightGreyTransparent
                )
                Text(text = "${forecastDay.astro.sunrise} / ${forecastDay.astro.sunset}")
            }
        }
    }
}

@Composable
fun WeatherDayHourly(forecastDayHour: List<Hour>, isExpanded: Boolean) {
    val lazyListState = rememberLazyListState()
    if (isExpanded) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Weather hourly:")
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                tonalElevation = 20.dp
            ) {
                LazyRow(
                    modifier = Modifier.background(Color.White),
                    state = lazyListState
                ) {
                    items(items = forecastDayHour) { hourForecast ->
                        Column(
                            modifier = Modifier.background(Color.White),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            HourWeatherConditions(hourForecast)
                            Spacer(modifier = Modifier.height(8.dp))
                            HourPrecipitation(hourForecast = hourForecast)
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(
                                modifier = Modifier.width(70.dp),
                                color = Color.LightGray,
                                thickness = 1.dp
                            )
                            Text(
                                text = hourForecast.time.takeLast(5),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

private val Height = Modifier.height(16.dp)
private const val Fraction04 = 0.4f
