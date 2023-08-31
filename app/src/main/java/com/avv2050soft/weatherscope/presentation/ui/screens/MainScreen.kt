package com.avv2050soft.weatherscope.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Place
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.presentation.MainActivity
import com.avv2050soft.weatherscope.presentation.navigation.BottomNavigation
import com.avv2050soft.weatherscope.presentation.navigation.SavedLocations
import com.avv2050soft.weatherscope.presentation.navigation.TodayScreen
import com.avv2050soft.weatherscope.presentation.navigation.WeatherScopeNavHost
import com.avv2050soft.weatherscope.presentation.utils.getActivity
import com.avv2050soft.weatherscope.presentation.utils.navigateSingleTopTo
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    weatherViewModel: WeatherViewModel
) {
    val location by remember { weatherViewModel.locationStateFlow }
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {

        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = {
                weatherViewModel.getLocationFromPreferences()
                weatherViewModel.loadWeather(location)
            },
            indicator = { state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    scale = true,
                    backgroundColor = Color.White
                )
            }
        ) {
            WeatherScopeNavHost(
                navHostController = navController,
                weatherViewModel
            )
        }
    }
}

@Composable
fun FindLocationRow(
    navHostController: NavHostController,
    weatherViewModel: WeatherViewModel
) {
    val activity = LocalContext.current.getActivity<MainActivity>()
    val location by remember { weatherViewModel.locationStateFlow }
    val weather by remember { weatherViewModel.weatherStateFlow }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(FindLocationHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .clickable { navHostController.navigateSingleTopTo(SavedLocations.route) }
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(all = 8.dp)
            ) {
                Icon(
                    modifier = Modifier.wrapContentHeight(),
                    imageVector = Icons.TwoTone.Search,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(start = 8.dp),
                    text = "${weather?.location?.name}, ${weather?.location?.country}",
                    textAlign = TextAlign.Left,
                    maxLines = 2
                )
            }
        }
        Icon(
            modifier = Modifier
                .clickable {
                    activity?.checkPermissions()
                    weatherViewModel.getLocationFromPreferences()
                    weatherViewModel.loadWeather(location)
                    navHostController.navigateSingleTopTo(route = TodayScreen.route)
                }
                .size(FindLocationHeight),
            imageVector = Icons.TwoTone.Place,
            contentDescription = "Consider my location button"
        )
    }
}

private val FindLocationHeight = 72.dp

