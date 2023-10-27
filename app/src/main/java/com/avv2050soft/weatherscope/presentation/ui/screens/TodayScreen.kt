package com.avv2050soft.weatherscope.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.R
import com.avv2050soft.weatherscope.domain.models.forecast.Hour
import com.avv2050soft.weatherscope.domain.models.forecast.Weather
import com.avv2050soft.weatherscope.presentation.ui.theme.LightGreyTransparent
import com.avv2050soft.weatherscope.presentation.utils.LoadImage
import com.avv2050soft.weatherscope.presentation.utils.formattedDate
import kotlin.math.roundToInt

@Composable
fun TodayScreen(
    navHostController: NavHostController,
    screenKey: String,
    weatherViewModel: WeatherViewModel
) {
    weatherViewModel.getLocationFromPreferences()
    val location by remember { weatherViewModel.locationStateFlow }
    weatherViewModel.loadWeather(location)
    val weather by remember { weatherViewModel.weatherStateFlow }
    val hourlyForecast = weather?.forecast?.forecastday?.get(0)?.hour
    if (weather != null) {
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
                        .padding(top = 0.dp, bottom = 55.dp),
                ) {
                    item {
                        weather?.let { weather ->
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
                            Spacer(modifier = Modifier.height(32.dp))

                            hourlyForecast?.let {
                                WeatherHourly(
                                    it,
                                    screenKey,
                                    weatherViewModel
                                )
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                            WeatherNow(weather = weather)
                            Spacer(modifier = Modifier.height(32.dp))
                            ForecastDay(weather = weather)
                            Spacer(modifier = Modifier.height(32.dp))
                            SunriseSunset(weather = weather)
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
            painter = painterResource(id = R.drawable.icon_day),
            contentDescription = null
        )
        Text(text = "Day:", modifier = Modifier.padding(end = 4.dp))
        Text(text = weather.forecast.forecastday[0].day.maxtempC.roundToInt().toString())
        Text(text = "°", modifier = Modifier.padding(end = 24.dp))
        Icon(
            painter = painterResource(id = R.drawable.icon_night),
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
private fun WeatherConditions(weather: Weather) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadImage(
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
fun WeatherHourly(
    hourlyForecast: List<Hour>,
    screenKey: String,
    weatherViewModel: WeatherViewModel
) {

    val lazyListState = weatherViewModel.scrollStates.getOrPut(screenKey) { LazyListState() }
    DisposableEffect(lazyListState) {
        onDispose {
            weatherViewModel.scrollStates[screenKey] = lazyListState
        }
    }
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
                items(items = hourlyForecast) { hourForecast ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HourWeatherConditions(hourForecast)
                        Spacer(modifier = Modifier.height(8.dp))
                        HourPrecipitation(hourForecast)
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

@Composable
fun HourWeatherConditions(
    hourForecast: Hour
) {
    val tempFontSize = 16.sp
    Row {
        Text(
            hourForecast.tempC.roundToInt().toString(),
            fontSize = tempFontSize,
            color = Color.Black
        )
        Text(text = "°", fontSize = tempFontSize, color = Color.Black)
    }
    LoadImage(
        data = "https:${hourForecast.condition.icon}",
        Modifier.size(50.dp),
        contentDescription = "Picture of the weather conditions",
        alignment = Alignment.BottomCenter
    )
}

@Composable
fun HourPrecipitation(
    hourForecast: Hour
) {
    val precipitationFontSize = 16.sp
    var precipitationChance = hourForecast.chanceOfRain.toString()
    var precipitationIcon = painterResource(id = R.drawable.water_drop_empty)
    if (hourForecast.chanceOfRain > 0) {
        precipitationChance = hourForecast.chanceOfRain.toString()
        precipitationIcon = painterResource(id = R.drawable.drop)
    }
    if (hourForecast.chanceOfSnow > 0) {
        precipitationChance = hourForecast.chanceOfSnow.toString()
        precipitationIcon = painterResource(id = R.drawable.snowflake)
    }
    Row {
        Text(
            text = precipitationChance,
            fontSize = precipitationFontSize,
            color = Color.Black
        )
        Text(text = "%", fontSize = precipitationFontSize, color = Color.Black)
    }
    Image(
        painter = precipitationIcon,
        modifier = Modifier.size(20.dp),
        contentDescription = ""
    )
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = hourForecast.precipMm.toString(),
            fontSize = precipitationFontSize,
            color = Color.Black
        )
        Text(
            text = "mm",
            fontSize = 12.sp,
            color = Color.Black,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun WeatherNow(weather: Weather) {
    Column {
        Text(text = "Now:")
        Spacer(modifier = Modifier.height(4.dp))
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
            Text(text = "${weather.current.windKph.roundToInt()} kph ${weather.current.windDir}")
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
            Text(text = "${weather.current.humidity}%")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Pressure",
                modifier = Modifier.fillMaxWidth(Fraction04),
                color = LightGreyTransparent
            )
            Text(text = "${weather.current.pressureMb.roundToInt()} mBar")
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
            Text(text = "${weather.current.uv.roundToInt()}")
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
            Text(text = "${weather.current.visKm.roundToInt()} km")
        }

    }
}

@Composable
private fun ForecastDay(weather: Weather) {
    Column {
        Text(text = "Total today:")
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Precipitation",
                modifier = Modifier.fillMaxWidth(Fraction04),
                color = LightGreyTransparent
            )
            Text(text = "${weather.forecast.forecastday[0].day.totalprecipMm} mm")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Snow",
                modifier = Modifier.fillMaxWidth(Fraction04),
                color = LightGreyTransparent
            )
            Text(text = "${weather.forecast.forecastday[0].day.totalsnowCm} cm")
        }
    }
}

@Composable
private fun SunriseSunset(weather: Weather) {
    Column {
        Text(text = "Sunrise and Sunset")
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
                    text = weather.forecast.forecastday[0].astro.sunrise,
                    fontSize = 28.sp, fontWeight = FontWeight.Thin
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Sunset", color = LightGreyTransparent)
                Text(
                    text = weather.forecast.forecastday[0].astro.sunset,
                    fontSize = 28.sp, fontWeight = FontWeight.Thin
                )
            }
        }
    }
}

private const val Fraction04 = 0.4f


