package com.avv2050soft.weatherscope.data.repository

import android.content.Context
import com.avv2050soft.weatherscope.data.local.db.WeatherDatabase
import com.avv2050soft.weatherscope.data.local.entities.LocationInDbItem
import com.avv2050soft.weatherscope.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    context: Context
): DatabaseRepository {

    private val db = WeatherDatabase.getInstance(context)

    override suspend fun insertInDb(locationInDbItem: LocationInDbItem) {
        db.locationsDao().insert(locationInDbItem)
    }

    override suspend fun getAllLocationItemsFromDb(): List<LocationInDbItem> {
        return db.locationsDao().getAllLocationItems()
    }

    override suspend fun deleteAllLocationItemsFromDb() {
        db.locationsDao().deleteAllLocationItems()
    }

    override suspend fun deleteLocationItemFromDbById(itemId: Int) {
        db.locationsDao().deleteLocationItemById(itemId)
    }
}