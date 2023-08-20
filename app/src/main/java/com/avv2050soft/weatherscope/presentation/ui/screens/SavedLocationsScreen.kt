package com.avv2050soft.weatherscope.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowForward
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material.icons.twotone.Place
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.presentation.navigation.AutocompleteLocations
import com.avv2050soft.weatherscope.presentation.utils.navigateSingleTopTo

@Composable
fun SavedLocationsScreen(
    modifier: Modifier,
    navHostController: NavHostController,
) {
    val weatherViewModel = hiltViewModel<WeatherViewModel>()
    val allLocationItemsFromDb by remember { weatherViewModel.locationsInDbStateFlow }

    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
    ) {
        FindLocationEditTextSaved(navHostController)
        Text(text = "Saved Locations")
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp, bottom = 90.dp)
        ) {
            items(items = allLocationItemsFromDb) {
                Row(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 8.dp)
                        .defaultMinSize(minHeight = 56.dp)
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .clickable {
                                val locationString = "${it.lat},${it.lon},${it.name},${it.country}"
                                weatherViewModel.saveLocationToPreferences(locationString)
                                weatherViewModel.getLocationFromPreferences()
                                weatherViewModel.updateEditTextValue("")
                                navHostController.navigateUp()
                            },
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            modifier = Modifier
                                .wrapContentHeight()
                                .size(32.dp),
                            imageVector = Icons.TwoTone.Place,
                            contentDescription = null,
                        )
                        Text(
                            text = "${it.name}, ${it.country}",
                            modifier = Modifier.padding(all = 8.dp)
                        )
                    }
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .wrapContentHeight()
                            .size(32.dp)
                            .clickable {
                                weatherViewModel.deleteLocationItemFromDbById(it.id)
                                weatherViewModel.getAllLocationItemsFromDb()
                            },
                        imageVector = Icons.TwoTone.Clear,
                        contentDescription = "Add to favorite locations"
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindLocationEditTextSaved(
    navHostController: NavHostController
) {
    val weatherViewModel = hiltViewModel<WeatherViewModel>()
    weatherViewModel.getAllLocationItemsFromDb()
    Column()
    {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) {
//                        navHostController.popBackStack()
                        navHostController.navigateSingleTopTo(AutocompleteLocations.route)
                    }
                }
                .fillMaxWidth(1f),
            label = { Text("Find location") },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable { navHostController.navigateSingleTopTo(AutocompleteLocations.route) },
                    imageVector = Icons.TwoTone.ArrowForward,
                    contentDescription = "Go back to SavedLocations screen"
                )
            },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}

