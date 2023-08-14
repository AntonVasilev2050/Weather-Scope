package com.avv2050soft.weatherscope.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.presentation.navigation.AutocompleteLocations
import com.avv2050soft.weatherscope.presentation.navigation.SavedLocations
import com.avv2050soft.weatherscope.presentation.utils.navigateSingleTopTo

@Composable
fun SavedLocationsScreen(
    modifier: Modifier,
    weatherViewModel: WeatherViewModel,
    navHostController: NavHostController,
) {
//    val autocomplete by remember { weatherViewModel.autocompleteStateFlow }.collectAsState()
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
    ) {
        FindLocationEditText(weatherViewModel, navHostController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindLocationEditText(weatherViewModel: WeatherViewModel, navHostController: NavHostController) {
    var text by rememberSaveable { mutableStateOf(weatherViewModel.editTextValue) }
//    var text by rememberSaveable { mutableStateOf("") }
    weatherViewModel.loadAutocomplete(text)

    OutlinedTextField(
//        value = text,
        value = weatherViewModel.editTextValue,
        onValueChange = {
            weatherViewModel.updateEditTextValue(it)
            text = it
            if (text.isBlank()) {
                navHostController.popBackStack()
                navHostController.navigateSingleTopTo(SavedLocations.route)
            } else {
                if ((text.length > 3) && (navHostController.currentDestination !=
                            navHostController.findDestination(AutocompleteLocations.route) )
                ) {
                    navHostController.popBackStack()
                    navHostController.navigateSingleTopTo(AutocompleteLocations.route)
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth(1f),
        label = { Text("Find location") },
        singleLine = true
    )
    Spacer(modifier = Modifier.height(8.dp))
    Divider(color = Color.LightGray, thickness = 1.dp)
}

