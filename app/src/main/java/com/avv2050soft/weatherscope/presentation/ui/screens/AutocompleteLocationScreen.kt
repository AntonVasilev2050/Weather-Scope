package com.avv2050soft.weatherscope.presentation.ui.screens

import android.content.Context
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem
import com.avv2050soft.weatherscope.presentation.navigation.SavedLocations
import com.avv2050soft.weatherscope.presentation.utils.navigateSingleTopTo
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.singleOrNull

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
        FindLocationEditTextAutocomplete(weatherViewModel, navHostController)
        Text(text = "Autocomplete")
        LazyColumn {
            items(items = autocomplete) {
                Row(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 56.dp)
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .clickable {
                            val locationString = "${it.lat},${it.lon},${it.name},${it.country}"
                            weatherViewModel.saveLocationToPreferences(locationString)
                            weatherViewModel.getLocationFromPreferences()
                            weatherViewModel.updateEditTextValue("")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindLocationEditTextAutocomplete(weatherViewModel: WeatherViewModel, navHostController: NavHostController) {
    var text by rememberSaveable { mutableStateOf(weatherViewModel.editTextValue) }
    val focusRequester = remember { FocusRequester() }
    weatherViewModel.loadAutocomplete(text)
    ShowKeyboardOnStartAndFocus(focusRequester)
    OutlinedTextField(
        value = text,
        onValueChange = {
            weatherViewModel.updateEditTextValue(it)
            text = it
            if (text.isEmpty()) {
                navHostController.popBackStack()
                navHostController.navigateSingleTopTo(SavedLocations.route)
            }
            focusRequester.requestFocus() // Восстанавливаем фокус на OutlinedTextField
        },
        modifier = Modifier
            .fillMaxWidth(1f)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    focusRequester.requestFocus()
                }
            },
        label = { Text("Find location") },
        singleLine = true,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Divider(color = Color.LightGray, thickness = 1.dp)
}

@Composable
fun ShowKeyboardOnStartAndFocus(focusRequester: FocusRequester) {
    val rootView = LocalView.current
    val context = LocalContext.current

    DisposableEffect(rootView) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            rootView.requestFocus()
            inputMethodManager.showSoftInput(rootView, InputMethodManager.SHOW_IMPLICIT)

            // Получаем фокус на OutlinedTextField
            focusRequester.requestFocus()
        }
        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}
