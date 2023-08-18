package com.avv2050soft.weatherscope.domain.repository

import com.avv2050soft.weatherscope.data.local.dto.LocationItemDto

interface DatabaseRepository {
    suspend fun insertInDb(locationInDbItem: LocationItemDto)
    suspend fun getAllLocationItemsFromDb():List<LocationItemDto>
    suspend fun deleteAllLocationItemsFromDb()
    suspend fun deleteLocationItemFromDbById(itemId: Int)
}