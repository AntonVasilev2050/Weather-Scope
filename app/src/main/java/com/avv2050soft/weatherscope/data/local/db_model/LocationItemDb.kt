package com.avv2050soft.weatherscope.data.local.db_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_items")
data class LocationItemDb(
    val country: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "lat")
    val lat: Double,
    @ColumnInfo(name = "lon")
    val lon: Double,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "region")
    val region: String,
    @ColumnInfo(name = "url")
    val url: String
)

