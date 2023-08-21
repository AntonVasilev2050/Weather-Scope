package com.avv2050soft.weatherscope.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avv2050soft.weatherscope.data.local.db_model.LocationItemDb
import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem

@Dao
interface LocationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationItemDb: LocationItemDb)

    @Query("SELECT * FROM location_items")
    suspend fun getAllLocationItems(): List<AutocompleteItem>

    @Query("DELETE FROM location_items")
    suspend fun deleteAllLocationItems()

    @Query("DELETE FROM location_items WHERE id = :itemId")
    suspend fun deleteLocationItemById(itemId: Int)
}
