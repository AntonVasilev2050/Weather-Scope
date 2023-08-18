package com.avv2050soft.weatherscope.domain.usecases

import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem
import com.avv2050soft.weatherscope.domain.repository.WeatherRepository
import javax.inject.Inject

class getAutocompleteListUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend fun search(location: String): List<AutocompleteItem> {
        return repository.search(location = location)
    }
}