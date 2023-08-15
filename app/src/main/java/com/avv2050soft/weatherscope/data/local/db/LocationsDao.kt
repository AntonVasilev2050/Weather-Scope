package com.avv2050soft.weatherscope.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avv2050soft.weatherscope.data.local.entities.LocationInDbItem

@Dao
interface LocationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationInDbItem: LocationInDbItem)

    @Query("SELECT * FROM location_items")
    suspend fun getAllLocationItems(): List<LocationInDbItem>

    @Query("DELETE FROM location_items")
    suspend fun deleteAllLocationItems()

    @Query("DELETE FROM location_items WHERE id = :itemId")
    suspend fun deleteLocationItemById(itemId: Int)
}
