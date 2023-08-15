package com.avv2050soft.weatherscope.presentation.ui.screens

import android.content.Context
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
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
    var text by rememberSaveable { mutableStateOf(weatherViewModel.editTextValue) }
    weatherViewModel.loadAutocomplete(text)
    Column(
//        modifier = Modifier.clickable {
//            navHostController.popBackStack()
//            navHostController.navigateSingleTopTo(AutocompleteLocations.route)
//        }
    )
    {
        OutlinedTextField(
            value = text,
            onValueChange = {
            },
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindLocationEditText(weatherViewModel: WeatherViewModel, navHostController: NavHostController) {
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
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    focusRequester.requestFocus()
                }
            }
            .fillMaxWidth(1f),

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

