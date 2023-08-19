package com.avv2050soft.weatherscope.presentation.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem
import com.avv2050soft.weatherscope.domain.models.forecast.Weather
import com.avv2050soft.weatherscope.domain.repository.SharedPreferencesRepository
import com.avv2050soft.weatherscope.domain.usecases.DeleteLocationItemFromDatabaseByIdUseCase
import com.avv2050soft.weatherscope.domain.usecases.GetAllLocationItemsFromDatabaseUseCase
import com.avv2050soft.weatherscope.domain.usecases.GetAutocompleteListUseCase
import com.avv2050soft.weatherscope.domain.usecases.GetWeatherUseCase
import com.avv2050soft.weatherscope.domain.usecases.InsertInDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


const val LocationNameKey = "location name key"
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getAutocompleteListUseCase: GetAutocompleteListUseCase,
    private val getAllLocationItemsFromDatabaseUseCase: GetAllLocationItemsFromDatabaseUseCase,
    private val insertInDatabaseUseCase: InsertInDatabaseUseCase,
    private val deleteLocationItemFromDatabaseByIdUseCase: DeleteLocationItemFromDatabaseByIdUseCase

) : ViewModel() {
    private var weather: Weather? = null
    private val _weatherStateFlow = MutableStateFlow(weather)
    val weatherStateFlow = _weatherStateFlow.asStateFlow()

    private val _message = Channel<String>()
    val message = _message.receiveAsFlow()

    private var location: String = ""
    private val _locationStateFlow = MutableStateFlow(location)
    val locationStateFlow = _locationStateFlow.asStateFlow()

    private var autocomplete = emptyList<AutocompleteItem>()
    private val _autocompleteStateFlow = MutableStateFlow(autocomplete)
    val autocompleteStateFlow = _autocompleteStateFlow.asStateFlow()

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
                .onSuccess { _autocompleteStateFlow.value = autocomplete }
                .onFailure {
                    _message.send("An error occurred when getting autocomplete")
                    Log.d("data_test", it.message.toString())
                }
        }
    }

    fun loadWeather(location: String) {
        viewModelScope.launch {
            runCatching {
                weather = getWeatherUseCase.getWeather(
                    location = location,
                    days = 14,
                    aqi = "yes",
                    alerts = "yes",
                    lang = "en"
                )
            }
                .onSuccess { _weatherStateFlow.value = weather }
                .onFailure {
                    _message.send("An error occurred when getting weather")
                    Log.d("data_test", it.message.toString())
                }
        }
    }

    fun getLocationFromPreferences() {
        viewModelScope.launch {
            runCatching {
                location = sharedPreferencesRepository.getString(LocationNameKey, "Krasnodar") ?: ""
            }
                .onSuccess { _locationStateFlow.value = location }
                .onFailure {
                    _message.send("An error occurred when getting location from preferences")
                    Log.d("data_test", it.message.toString())
                }
        }
    }

    fun saveLocationToPreferences(location: String) {
        viewModelScope.launch {
            runCatching {
                sharedPreferencesRepository.saveString(LocationNameKey, location)
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

    private var locationsInDb = emptyList<AutocompleteItem>()
    private val _locationsInDbStateFlow = MutableStateFlow(locationsInDb)
    val locationsInDbStateFlow = _locationsInDbStateFlow.asStateFlow()

    fun getAllLocationItemsFromDb() {
        viewModelScope.launch {
            runCatching {
                locationsInDb = getAllLocationItemsFromDatabaseUseCase.getAllLocationItemsFromDb()
            }
                .onSuccess {
                    _locationsInDbStateFlow.value = locationsInDb
                }
                .onFailure {
                    _message.send("An error occurred when ")
                    Log.d("data_test", it.message.toString())
                }
        }
    }

    fun deleteLocationItemFromDbById(itemId: Int){
        viewModelScope.launch {
            deleteLocationItemFromDatabaseByIdUseCase.deleteLocationItemFromDbById(itemId)
        }

    }
}