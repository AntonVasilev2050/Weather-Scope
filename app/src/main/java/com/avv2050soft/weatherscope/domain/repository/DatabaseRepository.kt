package com.avv2050soft.weatherscope.domain.repository

import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem

interface DatabaseRepository {
    suspend fun insertInDb(autocompleteItem: AutocompleteItem)
    suspend fun getAllLocationItemsFromDb(): List<AutocompleteItem>
    suspend fun deleteAllLocationItemsFromDb()
    suspend fun deleteLocationItemFromDbById(itemId: Int)
}