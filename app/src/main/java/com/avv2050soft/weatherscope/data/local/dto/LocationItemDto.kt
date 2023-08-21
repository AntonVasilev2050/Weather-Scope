package com.avv2050soft.weatherscope.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "location_items")
data class LocationItemDto(
    val country: String,
    @PrimaryKey
    val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val url: String
)

