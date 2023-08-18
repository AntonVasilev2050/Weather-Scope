package com.avv2050soft.weatherscope.domain.usecases

import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem
import com.avv2050soft.weatherscope.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetAllLocationItemsFromDatabaseUseCase @Inject constructor(
    private  val repository: DatabaseRepository
) {
    suspend fun getAllLocationItemsFromDb(): List<AutocompleteItem> {
        return repository.getAllLocationItemsFromDb()
    }
}