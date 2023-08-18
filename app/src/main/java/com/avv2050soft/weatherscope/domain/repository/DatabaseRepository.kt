package com.avv2050soft.weatherscope.domain.repository

import com.avv2050soft.weatherscope.data.local.dto.LocationItemDto
import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem

interface DatabaseRepository {
    suspend fun insertInDb(locationInDbItem: LocationItemDto)
    suspend fun getAllLocationItemsFromDb():List<AutocompleteItem>
    suspend fun deleteAllLocationItemsFromDb()
    suspend fun deleteLocationItemFromDbById(itemId: Int)
}