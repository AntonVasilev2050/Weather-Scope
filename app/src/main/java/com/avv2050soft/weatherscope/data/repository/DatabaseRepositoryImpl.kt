package com.avv2050soft.weatherscope.data.repository

import android.content.Context
import com.avv2050soft.weatherscope.data.local.db.WeatherDatabase
import com.avv2050soft.weatherscope.data.mappers.AutocompleteItemMapper
import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem
import com.avv2050soft.weatherscope.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    context: Context,
    private val mapper: AutocompleteItemMapper
) : DatabaseRepository {

    private val db = WeatherDatabase.getInstance(context)

    override suspend fun insertInDb(autocompleteItem: AutocompleteItem) {
        val locationItemDto = mapper.mapAutocompleteItemToLocationItemDto(autocompleteItem)
        db.locationsDao().insert(locationItemDto)
    }

    override suspend fun getAllLocationItemsFromDb(): List<AutocompleteItem> {
        return db.locationsDao().getAllLocationItems()
    }

    override suspend fun deleteAllLocationItemsFromDb() {
        db.locationsDao().deleteAllLocationItems()
    }

    override suspend fun deleteLocationItemFromDbById(itemId: Int) {
        db.locationsDao().deleteLocationItemById(itemId)
    }
}