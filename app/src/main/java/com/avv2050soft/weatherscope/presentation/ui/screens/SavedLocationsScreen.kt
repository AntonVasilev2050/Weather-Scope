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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.presentation.navigation.AutocompleteLocations
import com.avv2050soft.weatherscope.presentation.utils.navigateSingleTopTo

@Composable
fun SavedLocationsScreen(
    modifier: Modifier,
    weatherViewModel: WeatherViewModel,
    navHostController: NavHostController,
) {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
    ) {
        FindLocationEditTextSaved(weatherViewModel, navHostController)
        Text(text = "Saved Locations")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindLocationEditTextSaved(
    weatherViewModel: WeatherViewModel,
    navHostController: NavHostController
) {
    val text by rememberSaveable { mutableStateOf(weatherViewModel.editTextValue) }
    weatherViewModel.loadAutocomplete(text)
    Column()
    {
        OutlinedTextField(
            value = text,
            onValueChange = {},
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        navHostController.popBackStack()
                        navHostController.navigateSingleTopTo(AutocompleteLocations.route)
                    }
                }
                .fillMaxWidth(1f),
            label = { Text("Find location") },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}

