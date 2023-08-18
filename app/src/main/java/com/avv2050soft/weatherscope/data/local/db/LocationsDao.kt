package com.avv2050soft.weatherscope.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avv2050soft.weatherscope.data.local.dto.LocationItemDto

@Dao
interface LocationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationItemDto: LocationItemDto)

    @Query("SELECT * FROM location_items")
    suspend fun getAllLocationItems(): List<LocationItemDto>

    @Query("DELETE FROM location_items")
    suspend fun deleteAllLocationItems()

    @Query("DELETE FROM location_items WHERE id = :itemId")
    suspend fun deleteLocationItemById(itemId: Int)
}
