package com.avv2050soft.weatherscope.domain.usecases

import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem
import com.avv2050soft.weatherscope.domain.repository.DatabaseRepository
import javax.inject.Inject

class InsertInDatabaseUseCase @Inject constructor(
    private val repository: DatabaseRepository
){
    suspend fun insertInDb(autocompleteItem: AutocompleteItem){
        repository.insertInDb(autocompleteItem)
    }
}