package com.avv2050soft.weatherscope.domain.repository

import com.avv2050soft.weatherscope.data.local.entities.LocationInDbItem

interface DatabaseRepository {
    suspend fun insertInDb(locationInDbItem: LocationInDbItem)
    suspend fun getAllLocationItemsFromDb():List<LocationInDbItem>
    suspend fun deleteAllLocationItemsFromDb()
    suspend fun deleteLocationItemFromDbById(itemId: Int)
}