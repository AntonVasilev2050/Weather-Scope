package com.avv2050soft.weatherscope.data.local.db_model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_items")
data class LocationItemDb(
    val country: String,
    @PrimaryKey
    val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val url: String
)

