package com.avv2050soft.weatherscope.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Place
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@ExperimentalComposeUiApi
@Composable
fun AutocompleteLocationScreen(
    modifier: Modifier,
    weatherViewModel: WeatherViewModel,
    navHostController: NavHostController,
) {
    val autocomplete by remember { weatherViewModel.autocompleteStateFlow }.collectAsState()

    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
    ) {
        FindLocationEditText(weatherViewModel, navHostController)
        Text(text = "Autocomplete")
        LazyColumn {
            items(items = autocomplete) {
                Row(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 56.dp)
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .clickable {
                            weatherViewModel.saveLocationToPreferences("${it.lat},${it.lon},${it.name},${it.country}")
                            weatherViewModel.getLocationFromPreferences()
                            navHostController.navigateUp()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .wrapContentHeight()
                            .size(40.dp),
                        imageVector = Icons.TwoTone.Place,
                        contentDescription = null,
                    )
                    Text(
                        text = "${it.name}, ${it.country}",
                        modifier = Modifier.padding(all = 8.dp)
                    )
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
        }
    }
}
