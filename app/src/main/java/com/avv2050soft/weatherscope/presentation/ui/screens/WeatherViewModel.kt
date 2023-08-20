package com.avv2050soft.weatherscope.presentation.ui.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem
import com.avv2050soft.weatherscope.domain.models.forecast.Weather
import com.avv2050soft.weatherscope.domain.usecases.DeleteLocationItemFromDatabaseByIdUseCase
import com.avv2050soft.weatherscope.domain.usecases.GetAllLocationItemsFromDatabaseUseCase
import com.avv2050soft.weatherscope.domain.usecases.GetAutocompleteListUseCase
import com.avv2050soft.weatherscope.domain.usecases.GetLocationFromPreferencesUseCase
import com.avv2050soft.weatherscope.domain.usecases.GetWeatherUseCase
import com.avv2050soft.weatherscope.domain.usecases.InsertInDatabaseUseCase
import com.avv2050soft.weatherscope.domain.usecases.SaveLocationToPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


const val LocationNameKey = "location name key"

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getLocationFromPreferencesUseCase: GetLocationFromPreferencesUseCase,
    private val saveLocationToPreferencesUseCase: SaveLocationToPreferencesUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getAutocompleteListUseCase: GetAutocompleteListUseCase,
    private val getAllLocationItemsFromDatabaseUseCase: GetAllLocationItemsFromDatabaseUseCase,
    private val insertInDatabaseUseCase: InsertInDatabaseUseCase,
    private val deleteLocationItemFromDatabaseByIdUseCase: DeleteLocationItemFromDatabaseByIdUseCase

) : ViewModel() {
    private var weather: Weather? = null
    var weatherStateFlow: MutableState<Weather?> = mutableStateOf(weather)

    private val _message = Channel<String>()
    val message = _message.receiveAsFlow()

    private var location: String = ""
    val locationStateFlow: MutableState<String> = mutableStateOf(location)

    private var autocomplete: List<AutocompleteItem> = emptyList<AutocompleteItem>()
    val autocompleteStateFlow: MutableState<List<AutocompleteItem>> = mutableStateOf(autocomplete)

    private var locationsInDb : List<AutocompleteItem> = emptyList<AutocompleteItem>()
    val locationsInDbStateFlow : MutableState<List<AutocompleteItem>> = mutableStateOf(locationsInDb)

    var editTextValue by mutableStateOf("")
        private set

    fun updateEditTextValue(input: String) {
        editTextValue = input
    }

    fun loadAutocomplete(location: String) {
        viewModelScope.launch {
            runCatching {
                autocomplete = getAutocompleteListUseCase.search(location)
            }
                .onSuccess { autocompleteStateFlow.value = autocomplete }
                .onFailure {
                    _message.send("An error occurred when getting autocomplete")
                    Log.d("data_test", it.message.toString())
                }
        }
    }

    fun loadWeather(location: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                weather = getWeatherUseCase.getWeather(
                    location = location,
                    days = 14,
                    aqi = "yes",
                    alerts = "yes",
                    lang = "en"
                )
            }.onSuccess { weatherStateFlow.value = weather }
                .onFailure {
                    _message.send("An error occurred when getting weather")
                    Log.d("data_test", it.message.toString())
                }
        }
    }

    fun getLocationFromPreferences() {
        viewModelScope.launch {
            runCatching {
                location = getLocationFromPreferencesUseCase.getString(LocationNameKey, "Krasnodar") ?: ""
            }
                .onSuccess { locationStateFlow.value = location }
                .onFailure {
                    _message.send("An error occurred when getting location from preferences")
                    Log.d("data_test", it.message.toString())
                }
        }
    }

    fun saveLocationToPreferences(location: String) {
        viewModelScope.launch {
            runCatching {
                saveLocationToPreferencesUseCase.saveString(LocationNameKey, location)
            }
                .onSuccess { _message.send("Success when saving location to the preferences") }
                .onFailure {
                    _message.send("An error occurred when saving location to the preferences")
                    Log.d("data_test", it.message.toString())
                }
        }
    }

    fun insertInDatabase(autocompleteItem: AutocompleteItem) {
        viewModelScope.launch {
            insertInDatabaseUseCase.insertInDb(autocompleteItem)
        }
    }

    fun getAllLocationItemsFromDb() {
        viewModelScope.launch {
            runCatching {
                locationsInDb = getAllLocationItemsFromDatabaseUseCase.getAllLocationItemsFromDb()
            }
                .onSuccess {
                    locationsInDbStateFlow.value = locationsInDb
                }
                .onFailure {
                    _message.send("An error occurred when ")
                    Log.d("data_test", it.message.toString())
                }
        }
    }

    fun deleteLocationItemFromDbById(itemId: Int) {
        viewModelScope.launch {
            deleteLocationItemFromDatabaseByIdUseCase.deleteLocationItemFromDbById(itemId)
        }

    }
}